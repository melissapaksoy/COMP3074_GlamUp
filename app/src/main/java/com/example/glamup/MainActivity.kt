package com.example.glamup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.glamup.admin.AdminActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.google.firebase.firestore.FieldValue


class MainActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoogle: Button
    private lateinit var btnFacebook: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignup: TextView

    // Google sign-in launcher
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val cred = GoogleAuthProvider.getCredential(account.idToken, null)

                auth.signInWithCredential(cred)
                    .addOnSuccessListener {
                        val uid = auth.currentUser?.uid
                        if (uid == null) {
                            Toast.makeText(this, "No user id", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }

                        // Check if user doc exists; if not, create default CLIENT role
                        val usersRef = db.collection("users").document(uid)
                        usersRef.get()
                            .addOnSuccessListener { doc ->
                                if (doc.exists()) {
                                    val role = doc.getString("role") ?: "CLIENT"
                                    navigateToDashboard(role)
                                } else {
                                    val userData = hashMapOf(
                                        "uid" to uid,
                                        "email" to (account.email ?: ""),
                                        "role" to "CLIENT"
                                    )
                                    usersRef.set(userData)
                                        .addOnSuccessListener {
                                            navigateToDashboard("CLIENT")
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Failed to save user role",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Auth failed: ${it.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } catch (e: Exception) {
                Toast.makeText(this, "Google Sign-In canceled/failed", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       // --- ADMIN SEED ---
       seedAdminUser()


            // 🔥 TEST: simple Firestore write
        val db = FirebaseFirestore.getInstance()
        val testData = hashMapOf(
            "fromApp" to "GlamUp",
            "time" to FieldValue.serverTimestamp()
        )

        db.collection("debug")
            .document("testDoc")
            .set(testData)
            .addOnSuccessListener {
                Log.d("FIREBASE_TEST", "Successfully wrote testDoc")
            }
            .addOnFailureListener { e ->
                Log.e("FIREBASE_TEST", "Error writing testDoc", e)
            }

        // Bind views
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnFacebook = findViewById(R.id.btnFacebook)
        tvForgotPassword = findViewById(R.id.tvForgotPassword) // or R.id.tvForgot if that’s your id
        tvSignup = findViewById(R.id.tvSignup)

        // --- Email + Password login (single button) ---
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val uid = result.user?.uid
                    if (uid == null) {
                        Toast.makeText(this, "No user id", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }

                    db.collection("users").document(uid).get()
                        .addOnSuccessListener { doc ->
                            val role = doc.getString("role") ?: "CLIENT"
                            navigateToDashboard(role)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to load user role", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }

        // --- Google Login ---
        btnGoogle.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val client = GoogleSignIn.getClient(this, gso)
            googleSignInLauncher.launch(client.signInIntent)
        }

        // --- Facebook Login (still placeholder) ---
        btnFacebook.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/login")))
        }

        // Forgot password – you can later open a real reset screen
        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Forgot password clicked", Toast.LENGTH_SHORT).show()
        }

        // Sign up – Launches the new SignUpActivity
        tvSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }

    // --- TEMPORARY ADMIN SEED FUNCTION ---
    // Call this ONCE in onCreate, run the app, then remove it.
    private fun seedAdminUser() {
        val adminEmail = "admin@glamup.com"
        val adminPassword = "password123"

        // 1. Try to Sign In first (in case user already exists)
        auth.signInWithEmailAndPassword(adminEmail, adminPassword)
            .addOnSuccessListener { result ->
                // User exists! Update their role to ADMIN just to be sure.
                val uid = result.user?.uid
                if (uid != null) {
                    updateAdminRole(uid)
                }
            }
            .addOnFailureListener {
                // 2. If Sign In fails, try creating the user
                auth.createUserWithEmailAndPassword(adminEmail, adminPassword)
                    .addOnSuccessListener { result ->
                        val uid = result.user?.uid
                        if (uid != null) {
                            updateAdminRole(uid)
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("ADMIN_SEED", "Could not create or sign in admin", e)
                        Toast.makeText(this, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
            }
    }

    // Helper to force the role in Firestore
    private fun updateAdminRole(uid: String) {
        val adminData = hashMapOf(
            "uid" to uid,
            "email" to "admin@glamup.com",
            "role" to "ADMIN", // Force role to ADMIN
            "name" to "Super Admin"
        )
        db.collection("users").document(uid).set(adminData)
            .addOnSuccessListener {
                Log.d("ADMIN_SEED", "Admin role set successfully")
            }
            .addOnFailureListener { e ->
                Log.e("ADMIN_SEED", "Error setting admin role", e)
            }
    }



    private fun navigateToDashboard(role: String) {
        val intent = when (role.uppercase()) {
            "ADMIN" -> Intent(this, AdminActivity::class.java)
            "BEAUTYPRO" -> Intent(this, DashboardActivity::class.java)
            else -> Intent(this, HomeActivity::class.java) // CLIENT
        }
        startActivity(intent)
        finish()
    }
}

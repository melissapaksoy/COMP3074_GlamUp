package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.glamup.AdminActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Match EXACT ids from your XML
        val edtFirstName = findViewById<EditText>(R.id.edtFirstName)
        val edtLastName = findViewById<EditText>(R.id.edtLastName)
        val edtEmail = findViewById<EditText>(R.id.edtEmailSignup)
        val edtPassword = findViewById<EditText>(R.id.edtPasswordSignup)
        val edtConfirmPassword = findViewById<EditText>(R.id.edtConfirmPasswordSignup)

        val cbClient = findViewById<CheckBox>(R.id.cbClient)
        val cbBeautyPro = findViewById<CheckBox>(R.id.cbBeautyPro)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnBackToLogin = findViewById<Button>(R.id.btnBackToLogin)

        // --- NEW: Make Checkboxes Mutually Exclusive ---

        // If Client is checked, uncheck BeautyPro
        cbClient.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cbBeautyPro.isChecked = false
            }
        }

        // If BeautyPro is checked, uncheck Client
        cbBeautyPro.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cbClient.isChecked = false
            }
        }
        // -----------------------------------------------

        // "Sign In!" button → go back to login
        btnBackToLogin.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            val firstName = edtFirstName.text.toString().trim()
            val lastName = edtLastName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val confirmPassword = edtConfirmPassword.text.toString().trim()

            // Determine role
            // Note: Since we added the listeners above, it's impossible to have both checked,
            // but keeping the logic safe is good practice.
            val role = when {
                cbClient.isChecked -> "CLIENT"
                cbBeautyPro.isChecked -> "BEAUTYPRO"
                else -> null // none selected
            }

            // Basic validation
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (role == null) {
                Toast.makeText(this, "Select either Client OR BeautyPro", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create account in Firebase Auth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val uid = result.user?.uid ?: return@addOnSuccessListener

                    val userData = hashMapOf(
                        "uid" to uid,
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "email" to email,
                        "role" to role
                    )

                    // Save user in Firestore: users/{uid}
                    db.collection("users").document(uid).set(userData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()
                            // Save role locally for footer navigation
                            val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                            prefs.edit()
                                .putString("user_role", role)  // "CLIENT" or "BEAUTYPRO"
                                .apply()

                            navigateToDashboard(role)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to save user: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun navigateToDashboard(role: String) {
        val intent = when (role) {
            "BEAUTYPRO" -> Intent(this, DashboardActivity::class.java)
            "ADMIN" -> Intent(this, AdminActivity::class.java)
            else -> Intent(this, HomeActivity::class.java) // CLIENT
        }
        // Clear back stack so user can't go back to signup
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

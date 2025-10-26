package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.glamup.admin.AdminActivity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val cred = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(cred)
                    .addOnSuccessListener {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish() // optional
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Auth failed: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: Exception) {
                Toast.makeText(this, "Google Sign-In canceled/failed", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Grab views
        val btnAdmin     = findViewById<Button>(R.id.btnAdminLogin)
        val btnBeautyPro = findViewById<Button>(R.id.btnBeautyProLogin)
        val btnClient    = findViewById<Button>(R.id.btnClientLogin)
        val btnGoogle    = findViewById<Button>(R.id.btnGoogle)
        val btnFacebook  = findViewById<Button>(R.id.btnFacebook)
        val tvForgot     = findViewById<TextView>(R.id.tvForgot)
        val tvSignup     = findViewById<TextView>(R.id.tvSignup)

        // Simple helper
        fun goHome() = startActivity(Intent(this, HomeActivity::class.java))

        btnAdmin.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }
        btnBeautyPro.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        btnClient.setOnClickListener {
            goHome()
        }
        btnGoogle.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // from google-services.json
                .requestEmail()
                .build()
            val client = GoogleSignIn.getClient(this, gso)
            googleSignInLauncher.launch(client.signInIntent)
        }


        btnFacebook.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://www.facebook.com/login"))) }    // TODO: replace with real FB login
        tvForgot.setOnClickListener { goHome() }       // TODO: navigate to ForgotPasswordActivity
        tvSignup.setOnClickListener { goHome() }       // TODO: navigate to SignUpActivity
    }
}
package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This line connects this code to your XML layout
        setContentView(R.layout.activity_signup)

        // Find the "Sign In!" button from XML and handle the click
        val btnBackToLogin = findViewById<Button>(R.id.btnBackToLogin)
        btnBackToLogin.setOnClickListener {
            // Go back to Login Activity
            finish()
        }

        // Optional: Handle the actual Register button
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            // Add your registration logic here later
        }
    }
}

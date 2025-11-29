package com.example.glamup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // In SignUpActivity.kt
            SignUpScreen(
                onNavigateToLogin = { finish() },
                onSignUpClicked = { signUpData ->
                    // Handle the data here!
                    // e.g. createFirebaseUser(signUpData.email, signUpData.password)
                }
            )

        }
    }
}

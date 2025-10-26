package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.glamup.admin.AdminActivity

class MainActivity : AppCompatActivity() {

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
        btnGoogle.setOnClickListener { goHome() }      // TODO: replace with real Google sign-in
        btnFacebook.setOnClickListener { goHome() }    // TODO: replace with real FB login
        tvForgot.setOnClickListener { goHome() }       // TODO: navigate to ForgotPasswordActivity
        tvSignup.setOnClickListener { goHome() }       // TODO: navigate to SignUpActivity
    }
}

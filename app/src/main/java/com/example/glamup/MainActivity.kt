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

        // LOGIN BUTTONS
        val btnAdmin = findViewById<Button>(R.id.btnAdminLogin)
        val btnBeautyPro = findViewById<Button>(R.id.btnBeautyProLogin)
        val btnClient = findViewById<Button>(R.id.btnClientLogin)

        // SOCIAL LOGIN (dummy)
        val btnGoogle = findViewById<Button>(R.id.btnGoogle)
        val btnFacebook = findViewById<Button>(R.id.btnFacebook)

        // TEXT LINKS
        val tvForgot = findViewById<TextView>(R.id.tvForgot)
        val tvSignup = findViewById<TextView>(R.id.tvSignup)

        // === ASSIGN LISTENERS PROPERLY ===

        // Admin → Admin Panel
        btnAdmin.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        // Beauty Pro → Dashboard
        btnBeautyPro.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        // Client → Home screen
        btnClient.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        // Social Login → Just go home for now
        btnGoogle.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        btnFacebook.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        // Forgot password → placeholder
        tvForgot.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        // Signup → placeholder
        tvSignup.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}

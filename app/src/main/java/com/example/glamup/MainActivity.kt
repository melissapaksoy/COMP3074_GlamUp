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

        // Original buttons (dummy)
        val goHome = { startActivity(Intent(this, HomeActivity::class.java)) }
        findViewById<Button>(R.id.btnAdminLogin).setOnClickListener {
            goHome()
            findViewById<Button>(R.id.btnBeautyProLogin).setOnClickListener { goHome() }
            findViewById<Button>(R.id.btnClientLogin).setOnClickListener { goHome() }
            findViewById<Button>(R.id.btnGoogle).setOnClickListener { goHome() }
            findViewById<Button>(R.id.btnFacebook).setOnClickListener { goHome() }

            findViewById<TextView>(R.id.tvForgot).setOnClickListener { goHome() }
            findViewById<TextView>(R.id.tvSignup).setOnClickListener { goHome() }

            // === NEW LOGIN TYPE BUTTONS ===
            val btnAdmin = findViewById<Button>(R.id.btnAdminLogin)
            val btnBeautyPro = findViewById<Button>(R.id.btnBeautyProLogin)
            val btnClient = findViewById<Button>(R.id.btnClientLogin)

            // Navigation for each
            btnAdmin.setOnClickListener {
                startActivity(Intent(this, AdminActivity::class.java))
            }

            btnBeautyPro.setOnClickListener {
                startActivity(Intent(this, DashboardActivity::class.java))
            }

            btnClient.setOnClickListener {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }
}

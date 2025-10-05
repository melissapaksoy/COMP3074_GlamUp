package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Quick Actions
        val btnManageAvailability: Button = findViewById(R.id.btnManageAvailability)
        val btnAddService: Button = findViewById(R.id.btnAddService)

        // New Requests
        val btnAccept1: Button = findViewById(R.id.btnAccept1)
        val btnDecline1: Button = findViewById(R.id.btnDecline1)
        val btnAccept2: Button = findViewById(R.id.btnAccept2)
        val btnDecline2: Button = findViewById(R.id.btnDecline2)
        val btnAccept3: Button = findViewById(R.id.btnAccept3)
        val btnDecline3: Button = findViewById(R.id.btnDecline3)

        // Profile button
        val btnProfile: ImageButton = findViewById(R.id.btnProfile)

        // Handle Quick Actions
        btnManageAvailability.setOnClickListener {
            Toast.makeText(this, "Manage Availability clicked", Toast.LENGTH_SHORT).show()
        }

        btnAddService.setOnClickListener {
            Toast.makeText(this, "Add Service clicked", Toast.LENGTH_SHORT).show()
        }

        // Handle New Requests
        btnAccept1.setOnClickListener {
            Toast.makeText(this, "Request 1 Accepted", Toast.LENGTH_SHORT).show()
        }

        btnDecline1.setOnClickListener {
            Toast.makeText(this, "Request 1 Declined", Toast.LENGTH_SHORT).show()
        }

        btnAccept2.setOnClickListener {
            Toast.makeText(this, "Request 2 Accepted", Toast.LENGTH_SHORT).show()
        }

        btnDecline2.setOnClickListener {
            Toast.makeText(this, "Request 2 Declined", Toast.LENGTH_SHORT).show()
        }

        btnAccept3.setOnClickListener {
            Toast.makeText(this, "Request 3 Accepted", Toast.LENGTH_SHORT).show()
        }

        btnDecline3.setOnClickListener {
            Toast.makeText(this, "Request 3 Declined", Toast.LENGTH_SHORT).show()
        }

        // Handle Profile logo click
        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}

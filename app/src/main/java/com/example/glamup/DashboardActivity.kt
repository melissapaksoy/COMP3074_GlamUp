package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Back button
        val backButton = findViewById<FrameLayout>(R.id.backButtonContainer)
        backButton.setOnClickListener { finish() }

        // Quick action cards
        val cardManageServices = findViewById<LinearLayout>(R.id.cardManageServices)
        val cardSetAvailability = findViewById<LinearLayout>(R.id.cardSetAvailability)
        val cardPortfolio = findViewById<LinearLayout>(R.id.cardPortfolio)
        val cardClientReviews = findViewById<LinearLayout>(R.id.cardClientReviews)

        cardManageServices.setOnClickListener {
            Toast.makeText(this, "Manage Services (UI only)", Toast.LENGTH_SHORT).show()
        }

        cardSetAvailability.setOnClickListener {
            Toast.makeText(this, "Set Availability (UI only)", Toast.LENGTH_SHORT).show()
        }

        cardPortfolio.setOnClickListener {
            Toast.makeText(this, "Portfolio Management (UI only)", Toast.LENGTH_SHORT).show()
        }

        cardClientReviews.setOnClickListener {
            startActivity(Intent(this, RatingsActivity::class.java))
        }

        // New Requests buttons (just show toasts)
        val btnAccept1 = findViewById<Button>(R.id.btnAccept1)
        val btnDecline1 = findViewById<Button>(R.id.btnDecline1)
        val btnAccept2 = findViewById<Button>(R.id.btnAccept2)
        val btnDecline2 = findViewById<Button>(R.id.btnDecline2)
        val btnAccept3 = findViewById<Button>(R.id.btnAccept3)
        val btnDecline3 = findViewById<Button>(R.id.btnDecline3)

        btnAccept1.setOnClickListener {
            Toast.makeText(this, "Accepted Lisa Chen (demo only)", Toast.LENGTH_SHORT).show()
        }
        btnDecline1.setOnClickListener {
            Toast.makeText(this, "Declined Lisa Chen (demo only)", Toast.LENGTH_SHORT).show()
        }

        btnAccept2.setOnClickListener {
            Toast.makeText(this, "Accepted Rachel Adams (demo only)", Toast.LENGTH_SHORT).show()
        }
        btnDecline2.setOnClickListener {
            Toast.makeText(this, "Declined Rachel Adams (demo only)", Toast.LENGTH_SHORT).show()
        }

        btnAccept3.setOnClickListener {
            Toast.makeText(this, "Accepted Amira Patel (demo only)", Toast.LENGTH_SHORT).show()
        }
        btnDecline3.setOnClickListener {
            Toast.makeText(this, "Declined Amira Patel (demo only)", Toast.LENGTH_SHORT).show()
        }
    }
}

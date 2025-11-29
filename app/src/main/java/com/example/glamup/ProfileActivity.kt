package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        /* -----------------------------
              LOAD PROFILE NAME
           ----------------------------- */
        val selectedName = intent.getStringExtra("profile_name")
        selectedName?.let { name ->
            findViewById<TextView>(R.id.txtProfileName).text = name
        }

        /* -----------------------------
                     BACK BUTTON
           ----------------------------- */
        val backButton = findViewById<FrameLayout>(R.id.backButtonContainer)
        backButton.setOnClickListener {
            finish() // Works instantly
        }

        /* -----------------------------
                BOOK APPOINTMENT
           ----------------------------- */
        val bookButton = findViewById<Button>(R.id.btnBookAppointment)
        bookButton.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }

        /* -----------------------------
                RATINGS & REVIEWS
           ----------------------------- */
        val ratingButton = findViewById<Button>(R.id.btnRatingsReviews)
        ratingButton.setOnClickListener {
            val intent = Intent(this, RatingsActivity::class.java)
            startActivity(intent)
        }
    }
}

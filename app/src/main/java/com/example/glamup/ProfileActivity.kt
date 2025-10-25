package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val bookButton: Button = findViewById(R.id.btnBookAppointment)
        bookButton.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }

        val ratingButton: Button = findViewById(R.id.btnRatingsReviews)
        ratingButton.setOnClickListener {
            val intent = Intent(this, RatingsActivity::class.java)
            startActivity(intent)
        }
    }
}
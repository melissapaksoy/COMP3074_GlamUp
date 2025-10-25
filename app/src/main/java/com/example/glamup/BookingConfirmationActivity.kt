package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookingConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_confirmation)

        // Retrieve views
        val txtMessage: TextView = findViewById(R.id.txtBookingMessage)
        val txtDetails: TextView = findViewById(R.id.txtBookingDetails)
        val btnBack: Button = findViewById(R.id.btnBackToHome)

        // Get booking info from intent
        val service = intent.getStringExtra("SERVICE")
        val date = intent.getStringExtra("DATE")
        val time = intent.getStringExtra("TIME")

        // Display confirmation message
        txtMessage.text = "🎉 Booking Confirmed!"
        txtDetails.text = "Service: $service\nDate: $date\nTime: $time"

        // Navigate back to home
        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}

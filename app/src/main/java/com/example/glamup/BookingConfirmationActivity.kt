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

        // Get data from intent
        val service = intent.getStringExtra("SERVICE") ?: "Unknown Service"
        val date = intent.getStringExtra("DATE") ?: "Unknown Date"
        val time = intent.getStringExtra("TIME") ?: "Unknown Time"

        // Match correct TextViews from XML
        val txtService = findViewById<TextView>(R.id.txtService)
        val txtDate = findViewById<TextView>(R.id.txtDate)
        val txtTime = findViewById<TextView>(R.id.txtTime)
        val btnBack = findViewById<Button>(R.id.btnBackToHome)

        // Set dynamic values
        txtService.text = "Service: $service"
        txtDate.text = "Date: $date"
        txtTime.text = "Time: $time"

        // Button action
        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}

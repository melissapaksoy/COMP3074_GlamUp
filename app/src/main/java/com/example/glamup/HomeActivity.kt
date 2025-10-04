package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val searchBox = findViewById<EditText>(R.id.searchBox)
        val btnFilter = findViewById<Button>(R.id.btnFilter)
        val btnPrice = findViewById<Button>(R.id.btnPrice)
        val btnRating = findViewById<Button>(R.id.btnRating)
        val btnAvailability = findViewById<Button>(R.id.btnAvailability)

        btnFilter.setOnClickListener { Toast.makeText(this, "Filters clicked", Toast.LENGTH_SHORT).show() }
        btnPrice.setOnClickListener { Toast.makeText(this, "Price filter clicked", Toast.LENGTH_SHORT).show() }
        btnRating.setOnClickListener { Toast.makeText(this, "Rating filter clicked", Toast.LENGTH_SHORT).show() }
        btnAvailability.setOnClickListener { Toast.makeText(this, "Availability filter clicked", Toast.LENGTH_SHORT).show() }

        // “Book” buttons navigate to Booking screen (same as your friends)
        val intentBooking = Intent(this, BookingActivity::class.java)
        findViewById<Button>(R.id.btnBook1).setOnClickListener { startActivity(intentBooking) }
        findViewById<Button>(R.id.btnBook2).setOnClickListener { startActivity(intentBooking) }
        findViewById<Button>(R.id.btnBook3).setOnClickListener { startActivity(intentBooking) }

        searchBox.setOnEditorActionListener { v, _, _ ->
            Toast.makeText(this, "Searching: ${v.text}", Toast.LENGTH_SHORT).show()
            true
        }
    }
}


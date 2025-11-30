package com.example.glamup

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ExploreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        // Footer navigation
        setupFooterNavigation(this)

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Filter mock actions
        findViewById<LinearLayout>(R.id.chipPrice).setOnClickListener {
            Toast.makeText(this, "Sort: Price", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.chipAvailability).setOnClickListener {
            Toast.makeText(this, "Filter: Availability", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.chipService).setOnClickListener {
            Toast.makeText(this, "Filter: Service", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.chipRating).setOnClickListener {
            Toast.makeText(this, "Filter: Rating", Toast.LENGTH_SHORT).show()
        }
    }
}

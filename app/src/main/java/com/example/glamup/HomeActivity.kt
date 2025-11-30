package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // ===== Views =====
        val cardsContainer = findViewById<LinearLayout>(R.id.cardsContainer)
        val searchBox = findViewById<EditText>(R.id.searchBox)

        val spinnerService = findViewById<Spinner>(R.id.spinnerService)
        val spinnerPrice = findViewById<Spinner>(R.id.spinnerPrice)
        val spinnerRating = findViewById<Spinner>(R.id.spinnerRating)
        val spinnerAvailability = findViewById<Spinner>(R.id.spinnerAvailability)

        // ===== Cards =====
        val cardSarah = findViewById<LinearLayout>(R.id.card_sarah)
        val cardMaria = findViewById<LinearLayout>(R.id.card_maria)
        val cardEmily = findViewById<LinearLayout>(R.id.card_emily_chen)

        // ===== Profile image click → Open Profile =====
        findViewById<ImageView>(R.id.imgSarah).setOnClickListener {
            openProfile("Sarah Johnson")
        }

        findViewById<ImageView>(R.id.imgMaria).setOnClickListener {
            openProfile("Maria Rodriguez")
        }

        findViewById<ImageView>(R.id.imgEmily).setOnClickListener {
            openProfile("Emily Chen")
        }

        // ===== Book buttons → Also open Profile first =====
        findViewById<Button>(R.id.btnBook1).setOnClickListener {
            openProfile("Sarah Johnson")
        }

        findViewById<Button>(R.id.btnBook2).setOnClickListener {
            openProfile("Maria Rodriguez")
        }

        findViewById<Button>(R.id.btnBook3).setOnClickListener {
            openProfile("Emily Chen")
        }
    }

    // ===== Function to open Profile Activity =====
    private fun openProfile(name: String) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }
}

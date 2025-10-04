package com.example.glamup

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RatingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratings)

        val ratingBar: RatingBar = findViewById(R.id.ratingBar)
        val reviewInput: EditText = findViewById(R.id.edtReview)
        val addReviewButton: Button = findViewById(R.id.btnAddReview)

        addReviewButton.setOnClickListener {
            val rating = ratingBar.rating   // e.g. 4.0
            val reviewText = reviewInput.text.toString()

            Toast.makeText(
                this,
                "You gave $rating stars: $reviewText",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
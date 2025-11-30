package com.example.glamup

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RatingsActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth by lazy { FirebaseAuth.getInstance() }

    private lateinit var reviewsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratings)

        // ⭐ REQUIRED FOR FOOTER NAVIGATION (fixes crash)
        setupFooterNavigation(this)

        // BACK BUTTON
        val backButton = findViewById<FrameLayout>(R.id.backButtonContainer)
        backButton.setOnClickListener { finish() }

        reviewsContainer = findViewById(R.id.reviewsContainer)

        val ratingBar: RatingBar = findViewById(R.id.ratingBar)
        val reviewInput: EditText = findViewById(R.id.edtReview)
        val addReviewButton: Button = findViewById(R.id.btnAddReview)
        val tvYourReview: TextView = findViewById(R.id.tvYourReview)

        // Submit review
        addReviewButton.setOnClickListener {
            val rating = ratingBar.rating
            val reviewText = reviewInput.text.toString().trim()

            if (rating == 0f || reviewText.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please select a rating and write a review.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val user = auth.currentUser
            val review = Review(
                beautyProId = "maria_rodriguez",
                userId = user?.uid ?: "guest",
                userName = user?.displayName ?: "Guest",
                rating = rating,
                text = reviewText
            )

            db.collection("reviews")
                .add(review)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "Thanks for your review!",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Show summary
                    tvYourReview.visibility = View.VISIBLE
                    tvYourReview.text = "You rated ${review.rating.toInt()}★: ${review.text}"

                    // Reset inputs
                    ratingBar.rating = 0f
                    reviewInput.text.clear()

                    loadReviews()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Failed to save review. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        loadReviews()
    }

    private fun loadReviews() {
        db.collection("reviews")
            .whereEqualTo("beautyProId", "maria_rodriguez")
            .get()
            .addOnSuccessListener { snapshot ->
                reviewsContainer.removeAllViews()

                if (snapshot.isEmpty) {
                    val tv = TextView(this).apply {
                        text = "No reviews yet. Be the first to review!"
                        setTextColor(0xFF555555.toInt())
                        textSize = 14f
                    }
                    reviewsContainer.addView(tv)
                    return@addOnSuccessListener
                }

                for (doc in snapshot.documents) {
                    val review = doc.toObject(Review::class.java) ?: continue

                    val itemView = layoutInflater.inflate(
                        R.layout.item_review,
                        reviewsContainer,
                        false
                    )

                    val tvName = itemView.findViewById<TextView>(R.id.tvReviewerName)
                    val tvStars = itemView.findViewById<TextView>(R.id.tvReviewStars)
                    val tvText = itemView.findViewById<TextView>(R.id.tvReviewText)

                    tvName.text = review.userName

                    val fullStars = "★".repeat(review.rating.toInt())
                    val emptyStars = "☆".repeat(5 - review.rating.toInt())
                    tvStars.text = fullStars + emptyStars

                    tvText.text = review.text

                    reviewsContainer.addView(itemView)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load reviews.", Toast.LENGTH_SHORT).show()
            }
    }
}

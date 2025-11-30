package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private data class Pro(
        val name: String,
        val role: String,
        val price: Int,
        val rating: Double,
        val availableToday: Boolean,
        val card: View
    )

    private lateinit var cardsContainer: LinearLayout
    private lateinit var searchBox: EditText

    // Spinners
    private lateinit var spinnerService: Spinner
    private lateinit var spinnerPrice: Spinner
    private lateinit var spinnerRating: Spinner
    private lateinit var spinnerAvailability: Spinner

    private lateinit var pros: List<Pro>

    // State
    private var serviceFilter: String? = null
    private var priceAsc: Boolean = true
    private var minRatingMode: Int = 0
    private var onlyAvailableToday: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // ⭐ Your footer navigation
        setupFooterNavigation(this)

        // ===== Views =====
        cardsContainer = findViewById(R.id.cardsContainer)
        searchBox = findViewById(R.id.searchBox)

        spinnerService = findViewById(R.id.spinnerService)
        spinnerPrice = findViewById(R.id.spinnerPrice)
        spinnerRating = findViewById(R.id.spinnerRating)
        spinnerAvailability = findViewById(R.id.spinnerAvailability)

        // ===== Cards (your IDs) =====
        val cardSarah = findViewById<LinearLayout>(R.id.card_sarah)
        val cardMaria = findViewById<LinearLayout>(R.id.card_maria)
        val cardEmily = findViewById<LinearLayout>(R.id.card_emily_chen)

        // ===== Image → Profile =====
        findViewById<ImageView>(R.id.imgSarah).setOnClickListener {
            openProfile("Sarah Johnson")
        }
        findViewById<ImageView>(R.id.imgMaria).setOnClickListener {
            openProfile("Maria Rodriguez")
        }
        findViewById<ImageView>(R.id.imgEmily).setOnClickListener {
            openProfile("Emily Chen")
        }

        // ===== Book buttons → Profile =====
        findViewById<Button>(R.id.btnBook1).setOnClickListener {
            openProfile("Sarah Johnson")
        }
        findViewById<Button>(R.id.btnBook2).setOnClickListener {
            openProfile("Maria Rodriguez")
        }
        findViewById<Button>(R.id.btnBook3).setOnClickListener {
            openProfile("Emily Chen")
        }

        // ===== Dummy Data (same as friend) =====
        pros = listOf(
            Pro("Sarah Johnson", "hair", 85, 4.9, true, cardSarah),
            Pro("Maria Rodriguez", "nails", 45, 4.8, false, cardMaria),
            Pro("Emily Chen", "makeup", 95, 5.0, true, cardEmily)
        )

        // ===== Search =====
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
        })

        // ===== Spinners =====
        spinnerService.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                serviceFilter = when (position) {
                    1 -> "hair"
                    2 -> "nails"
                    3 -> "makeup"
                    else -> null
                }
                applyFilters()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerPrice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                priceAsc = (position == 0) // Low→High or High→Low
                applyFilters()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerRating.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                minRatingMode = position
                applyFilters()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerAvailability.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                onlyAvailableToday = (position == 1)
                applyFilters()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Initial load
        applyFilters()
    }

    // ====== Filtering System (same as your friend, adjusted to your IDs) ======
    private fun applyFilters() {
        val q = searchBox.text?.toString()?.trim()?.lowercase().orEmpty()

        var filtered = pros.filter { p ->
            val searchMatch =
                q.isEmpty() ||
                        p.name.lowercase().contains(q) ||
                        p.role.lowercase().contains(q)

            val serviceMatch = serviceFilter == null || p.role == serviceFilter

            val ratingMatch = when (minRatingMode) {
                1 -> p.rating >= 1.0 && p.rating < 2.0
                2 -> p.rating >= 2.0 && p.rating < 3.0
                3 -> p.rating >= 3.0 && p.rating < 4.0
                4 -> p.rating >= 4.0 && p.rating < 5.0
                5 -> p.rating >= 5.0
                else -> true
            }

            val availabilityMatch = !onlyAvailableToday || p.availableToday

            searchMatch && serviceMatch && ratingMatch && availabilityMatch
        }

        filtered = if (priceAsc) filtered.sortedBy { it.price }
        else filtered.sortedByDescending { it.price }

        // Hide all cards first
        pros.forEach { it.card.visibility = View.GONE }

        // Clear container
        cardsContainer.removeAllViews()

        // Add filtered cards
        filtered.forEach { p ->
            p.card.visibility = View.VISIBLE
            cardsContainer.addView(p.card)
        }
    }

    // ====== Profile ======
    private fun openProfile(name: String) {
        val i = Intent(this, ProfileActivity::class.java)
        i.putExtra("profile_name", name)
        startActivity(i)
    }
}

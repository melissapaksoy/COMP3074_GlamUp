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
    private var serviceFilter: String? = null   // null=All, "hair"/"nails"/"makeup"
    private var priceAsc: Boolean = true
    private var minRatingMode: Int = 0          // 0=All, 1..5 = min stars
    private var onlyAvailableToday: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // ====== Views ======
        cardsContainer = findViewById(R.id.cardsContainer)
        searchBox = findViewById(R.id.searchBox)

        spinnerService = findViewById(R.id.spinnerService)
        spinnerPrice = findViewById(R.id.spinnerPrice)
        spinnerRating = findViewById(R.id.spinnerRating)
        spinnerAvailability = findViewById(R.id.spinnerAvailability)

        // Cards
        val cardSarah = findViewById<LinearLayout>(R.id.cardSarah)
        val cardMaria = findViewById<LinearLayout>(R.id.cardMaria)
        val cardEmily = findViewById<LinearLayout>(R.id.cardEmily)

        // Profile images -> ProfileActivity
        findViewById<ImageView>(R.id.imgSarah).setOnClickListener { openProfile("Sarah Johnson") }
        findViewById<ImageView>(R.id.imgMaria).setOnClickListener { openProfile("Maria Rodriguez") }
        findViewById<ImageView>(R.id.imgEmily).setOnClickListener { openProfile("Emily Chen") }

        // Book buttons -> BookingActivity
        val bookIntent = Intent(this, BookingActivity::class.java)
        findViewById<Button>(R.id.btnBook1).setOnClickListener {
            openProfile("Sarah Johnson")
        }

        findViewById<Button>(R.id.btnBook2).setOnClickListener {
            openProfile("Maria Rodriguez")
        }

        findViewById<Button>(R.id.btnBook3).setOnClickListener {
            openProfile("Emily Chen")
        }


        //  Dummy data
        pros = listOf(
            Pro("Sarah Johnson", "hair",   price = 85, rating = 4.9, availableToday = true,  card = cardSarah),
            Pro("Maria Rodriguez", "nails", price = 45, rating = 4.8, availableToday = false, card = cardMaria),
            Pro("Emily Chen", "makeup",    price = 95, rating = 5.0, availableToday = true,  card = cardEmily)
        )

        //  Search
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
        })

        // Spinner listeners

        // Service: 0=All, 1=Hair, 2=Nails, 3=Makeup
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

        // Price: 0=Low->High, 1=High->Low
        spinnerPrice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                priceAsc = (position == 0)
                applyFilters()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Rating: 0=All, 1=1★, 2=2★, 3=3★, 4=4★, 5=5★
        spinnerRating.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                minRatingMode = position
                applyFilters()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Availability: 0=All, 1=Available today
        spinnerAvailability.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                onlyAvailableToday = (position == 1)
                applyFilters()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // render
        applyFilters()
    }

    private fun applyFilters() {
        val q = searchBox.text?.toString()?.trim()?.lowercase().orEmpty()

        //  Filter
        var filtered = pros.filter { p ->
            val searchMatch = q.isEmpty() ||
                    p.name.lowercase().contains(q) ||
                    p.role.lowercase().contains(q)

            val serviceMatch = serviceFilter == null || p.role == serviceFilter

            // Inside applyFilters(), replace the ratingMatch block with:
            val ratingMatch = when (minRatingMode) {
                1 -> p.rating >= 1.0 && p.rating < 2.0   // 1.0–1.9
                2 -> p.rating >= 2.0 && p.rating < 3.0   // 2.0–2.9
                3 -> p.rating >= 3.0 && p.rating < 4.0   // 3.0–3.9
                4 -> p.rating >= 4.0 && p.rating < 5.0   // 4.0–4.9
                5 -> p.rating >= 5.0                     // 5.0 only (treat as exact 5)
                else -> true                              // “All ratings”
            }


            val availabilityMatch = !onlyAvailableToday || p.availableToday

            searchMatch && serviceMatch && ratingMatch && availabilityMatch
        }

        // Sort (by price)
        filtered = if (priceAsc) filtered.sortedBy { it.price } else filtered.sortedByDescending { it.price }

        // Apply to UI: show/hide + reorder inside cardsContainer
        pros.forEach { it.card.visibility = View.GONE }

        cardsContainer.removeAllViews()
        filtered.forEach { p ->
            p.card.visibility = View.VISIBLE
            cardsContainer.addView(p.card)
        }
    }

    private fun openProfile(name: String) {
        val i = Intent(this, ProfileActivity::class.java)
        i.putExtra("profile_name", name)
        startActivity(i)
    }
}

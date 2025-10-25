package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu // <-- important

class HomeActivity : AppCompatActivity() {

    private data class Pro(
        val name: String,
        val role: String,      // "hair", "nails", "makeup"
        val price: Int,        // From $X
        val rating: Double,    // e.g., 4.8..5.0
        val availableToday: Boolean,
        val card: View
    )

    private lateinit var cardsContainer: LinearLayout
    private lateinit var searchBox: EditText

    // Dropdown buttons
    private lateinit var btnService: Button
    private lateinit var btnPrice: Button
    private lateinit var btnRating: Button
    private lateinit var btnAvailability: Button

    private lateinit var pros: List<Pro>

    // Filter state
    private var serviceFilter: String? = null       // null = all, else "hair"/"nails"/"makeup"
    private var priceAsc: Boolean = true
    private var ratingBucket: Int = 0               // 0=All, 1=1.0–1.9, 2=2.0–2.9, 3=3.0–3.9, 4=4.0–4.9, 5=5.0
    private var onlyAvailableToday: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Views
        cardsContainer = findViewById(R.id.cardsContainer)
        searchBox = findViewById(R.id.searchBox)

        btnService = findViewById(R.id.btnService)
        btnPrice = findViewById(R.id.btnPrice)
        btnRating = findViewById(R.id.btnRating)
        btnAvailability = findViewById(R.id.btnAvailability)

        val cardSarah = findViewById<LinearLayout>(R.id.cardSarah)
        val cardMaria = findViewById<LinearLayout>(R.id.cardMaria)
        val cardEmily = findViewById<LinearLayout>(R.id.cardEmily)

        // Dummy data
        pros = listOf(
            Pro("Sarah Johnson", "hair",   85, 4.9, true,  cardSarah),
            Pro("Maria Rodriguez", "nails", 45, 4.8, false, cardMaria),
            Pro("Emily Chen",     "makeup", 95, 5.0, true,  cardEmily)
        )

        // Search live filter
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
        })

        // Dropdowns
        btnService.setOnClickListener { showServiceMenu(it) }
        btnPrice.setOnClickListener { showPriceMenu(it) }
        btnRating.setOnClickListener { showRatingMenu(it) }
        btnAvailability.setOnClickListener { showAvailabilityMenu(it) }

        // Profile and booking
        findViewById<ImageView>(R.id.imgSarah).setOnClickListener { openProfile("Sarah Johnson") }
        findViewById<ImageView>(R.id.imgMaria).setOnClickListener { openProfile("Maria Rodriguez") }
        findViewById<ImageView>(R.id.imgEmily).setOnClickListener { openProfile("Emily Chen") }

        findViewById<Button>(R.id.btnBook1).setOnClickListener { startActivity(Intent(this, BookingActivity::class.java)) }
        findViewById<Button>(R.id.btnBook2).setOnClickListener { startActivity(Intent(this, BookingActivity::class.java)) }
        findViewById<Button>(R.id.btnBook3).setOnClickListener { startActivity(Intent(this, BookingActivity::class.java)) }

        applyFilters()
    }

    // --- Popup “dropdown” menus ---
    private fun showServiceMenu(anchor: View) {
        val menu = PopupMenu(this, anchor)
        menu.menu.add("All Services")
        menu.menu.add("Hair")
        menu.menu.add("Nails")
        menu.menu.add("Makeup")
        menu.setOnMenuItemClickListener {
            serviceFilter = when (it.title.toString()) {
                "Hair" -> "hair"
                "Nails" -> "nails"
                "Makeup" -> "makeup"
                else -> null
            }
            btnService.text = it.title
            applyFilters()
            true
        }
        menu.show()
    }

    private fun showPriceMenu(anchor: View) {
        val menu = PopupMenu(this, anchor)
        menu.menu.add("Low to High")
        menu.menu.add("High to Low")
        menu.setOnMenuItemClickListener {
            priceAsc = (it.title == "Low to High")
            btnPrice.text = "Price: ${it.title}"
            applyFilters()
            true
        }
        menu.show()
    }

    private fun showRatingMenu(anchor: View) {
        val menu = PopupMenu(this, anchor)
        menu.menu.add("All Ratings")
        menu.menu.add("1★ - 1.9★")
        menu.menu.add("2★ - 2.9★")
        menu.menu.add("3★ - 3.9★")
        menu.menu.add("4★ - 4.9★")
        menu.menu.add("5★ only")
        menu.setOnMenuItemClickListener {
            ratingBucket = when (it.title.toString()) {
                "1★ - 1.9★" -> 1
                "2★ - 2.9★" -> 2
                "3★ - 3.9★" -> 3
                "4★ - 4.9★" -> 4
                "5★ only"   -> 5
                else -> 0
            }
            btnRating.text = it.title
            applyFilters()
            true
        }
        menu.show()
    }

    private fun showAvailabilityMenu(anchor: View) {
        val menu = PopupMenu(this, anchor)
        menu.menu.add("All Availability")
        menu.menu.add("Available Today")
        menu.setOnMenuItemClickListener {
            onlyAvailableToday = (it.title == "Available Today")
            btnAvailability.text = it.title
            applyFilters()
            true
        }
        menu.show()
    }

    // --- Filter + sort + render ---
    private fun applyFilters() {
        val q = searchBox.text?.toString()?.trim()?.lowercase().orEmpty()

        var filtered = pros.filter { p ->
            val searchMatch = q.isEmpty() || p.name.lowercase().contains(q) || p.role.lowercase().contains(q)
            val serviceMatch = serviceFilter == null || p.role == serviceFilter
            val ratingMatch = when (ratingBucket) {
                1 -> p.rating >= 1.0 && p.rating < 2.0
                2 -> p.rating >= 2.0 && p.rating < 3.0
                3 -> p.rating >= 3.0 && p.rating < 4.0
                4 -> p.rating >= 4.0 && p.rating < 5.0
                5 -> p.rating == 5.0
                else -> true
            }
            val availabilityMatch = !onlyAvailableToday || p.availableToday
            searchMatch && serviceMatch && ratingMatch && availabilityMatch
        }

        filtered = if (priceAsc) filtered.sortedBy { it.price } else filtered.sortedByDescending { it.price }

        // Update UI
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

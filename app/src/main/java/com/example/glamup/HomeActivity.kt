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

        // Profile images -> ProfileActivity
        findViewById<ImageView>(R.id.imgSarah).setOnClickListener {
            openProfile("Sarah Johnson")
        }
        findViewById<ImageView>(R.id.imgMaria).setOnClickListener {
            openProfile("Maria Rodriguez")
        }
        findViewById<ImageView>(R.id.imgEmily).setOnClickListener {
            openProfile("Emily Chen")
        }

        // Book buttons -> also open ProfileActivity
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

    private fun openProfile(name: String) {
        val i = Intent(this, ProfileActivity::class.java)
        i.putExtra("profile_name", name)
        startActivity(i)
    }
}

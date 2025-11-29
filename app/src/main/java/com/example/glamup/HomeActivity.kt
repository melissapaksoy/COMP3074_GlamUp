package com.example.glamup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Profile images -> ProfileActivity
        val imgSarah  = findViewById<ImageView>(R.id.imgSarah)
        val imgMaria  = findViewById<ImageView>(R.id.imgMaria)
        val imgEmily  = findViewById<ImageView>(R.id.imgEmily)

        imgSarah.setOnClickListener { openProfile("Sarah Johnson") }
        imgMaria.setOnClickListener { openProfile("Maria Rodriguez") }
        imgEmily.setOnClickListener { openProfile("Emily Chen") }

        // Book buttons -> create NEW intent each time!
        findViewById<Button>(R.id.btnBook1).setOnClickListener {
            startActivity(Intent(this, BookingActivity::class.java))
        }
        findViewById<Button>(R.id.btnBook2).setOnClickListener {
            startActivity(Intent(this, BookingActivity::class.java))
        }
        findViewById<Button>(R.id.btnBook3).setOnClickListener {
            startActivity(Intent(this, BookingActivity::class.java))
        }
    }

    private fun openProfile(name: String) {
        val i = Intent(this, ProfileActivity::class.java)
        i.putExtra("profile_name", name)
        startActivity(i)
    }
}

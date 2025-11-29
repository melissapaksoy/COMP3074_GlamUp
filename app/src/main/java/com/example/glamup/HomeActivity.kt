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

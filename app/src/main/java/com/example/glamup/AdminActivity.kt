package com.example.glamup

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        // ✅ Same footer navigation as Home screen
        setupFooterNavigation(this)

        // ✅ Back arrow
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()   // just go back to previous screen
        }

        // ✅ Quick actions
        val addUserLayout = findViewById<LinearLayout>(R.id.actionAddUser)
        val blockUserLayout = findViewById<LinearLayout>(R.id.actionBlockUser)

        addUserLayout.setOnClickListener {
            // For now just show a toast (you can replace with real logic later)
            Toast.makeText(this, "Add User clicked", Toast.LENGTH_SHORT).show()
        }

        blockUserLayout.setOnClickListener {
            Toast.makeText(this, "Block User clicked", Toast.LENGTH_SHORT).show()
        }
    }
}






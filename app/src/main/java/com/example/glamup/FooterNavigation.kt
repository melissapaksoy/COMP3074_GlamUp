package com.example.glamup

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun setupFooterNavigation(activity: Activity) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val prefs = activity.getSharedPreferences("user_prefs", Activity.MODE_PRIVATE)

    val home = activity.findViewById<View>(R.id.navHome)
    val notif = activity.findViewById<View>(R.id.navNotif)
    val logoutBtn = activity.findViewById<Button>(R.id.btnLogoutFooter)

    fun navigateByRole(role: String) {
        val intent = when (role) {
            "BEAUTYPRO" -> Intent(activity, DashboardActivity::class.java)
            "ADMIN"     -> Intent(activity, AdminActivity::class.java)
            else        -> Intent(activity, HomeActivity::class.java) // CLIENT
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(intent)
    }

    fun resolveRoleAndNavigate() {
        val user = auth.currentUser
        if (user == null) {
            navigateByRole("CLIENT")
            return
        }

        val cachedRole = prefs.getString("user_role", null)
        val cachedUid  = prefs.getString("user_uid", null)

        if (cachedRole != null && cachedUid == user.uid) {
            navigateByRole(cachedRole)
            return
        }

        db.collection("users").document(user.uid).get()
            .addOnSuccessListener { doc ->
                val role = doc.getString("role") ?: "CLIENT"

                prefs.edit()
                    .putString("user_role", role)
                    .putString("user_uid", user.uid)
                    .apply()

                navigateByRole(role)
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Could not load user role", Toast.LENGTH_SHORT).show()
                navigateByRole("CLIENT")
            }
    }

    // HOME BUTTON
    home?.setOnClickListener {
        resolveRoleAndNavigate()
    }

    // NOTIFICATION BUTTON
    notif?.setOnClickListener {
        activity.startActivity(Intent(activity, NotificationActivity::class.java))
    }

    // LOGOUT BUTTON
    logoutBtn?.setOnClickListener {
        auth.signOut()

        // Clear cached user role
        prefs.edit().clear().apply()

        // Send to LOGIN SCREEN
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)

        Toast.makeText(activity, "Logged out", Toast.LENGTH_SHORT).show()
    }
}

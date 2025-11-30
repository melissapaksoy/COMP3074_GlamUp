package com.example.glamup

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.glamup.admin.AdminActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun setupFooterNavigation(activity: Activity) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val prefs = activity.getSharedPreferences("user_prefs", Activity.MODE_PRIVATE)

    val home = activity.findViewById<View>(R.id.navHome)
    val notif = activity.findViewById<View>(R.id.navNotif)

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
            // Not logged in → send to client home or login screen if you prefer
            navigateByRole("CLIENT")
            return
        }

        val cachedRole = prefs.getString("user_role", null)
        val cachedUid  = prefs.getString("user_uid", null)

        // ✅ Use cache ONLY if it belongs to the current logged-in user
        if (cachedRole != null && cachedUid == user.uid) {
            navigateByRole(cachedRole)
            return
        }

        // ❌ Cache is missing or from a different user → fetch from Firestore
        db.collection("users").document(user.uid).get()
            .addOnSuccessListener { doc ->
                val role = doc.getString("role") ?: "CLIENT"

                // Update cache for this user
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

    home?.setOnClickListener {
        resolveRoleAndNavigate()
    }

    notif?.setOnClickListener {
        activity.startActivity(Intent(activity, NotificationActivity::class.java))
    }
}

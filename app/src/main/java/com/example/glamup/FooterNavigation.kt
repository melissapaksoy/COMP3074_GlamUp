package com.example.glamup

import android.app.Activity
import android.content.Intent
import android.view.View

fun setupFooterNavigation(activity: Activity) {

    val home = activity.findViewById<View>(R.id.navHome)
    val notif = activity.findViewById<View>(R.id.navNotif)

    // HOME → Return to HomeActivity
    home.setOnClickListener {
        val i = Intent(activity, HomeActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(i)
    }

    // NOTIFICATIONS → Open notifications
    notif.setOnClickListener {
        activity.startActivity(Intent(activity, NotificationActivity::class.java))
    }
}

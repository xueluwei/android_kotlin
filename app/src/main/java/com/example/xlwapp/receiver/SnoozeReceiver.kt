package com.example.xlwapp.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.xlwapp.utils.eggtimer.snoozeNotification

class SnoozeReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        //create notification manager
        val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
        ) as NotificationManager

        //snooze notification(snoozeNotification(Context) is extension function)
        notificationManager.snoozeNotification(context)
    }
}
package com.example.xlwapp.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.xlwapp.R
import com.example.xlwapp.utils.eggtimer.sendNotification

class AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent?) {

        //create notification manager
        val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
        ) as NotificationManager

        //start send notification (sendNotification(String, Context) is extension function)
        notificationManager.sendNotification(context.getString(R.string.eggs_ready), context)
    }
}
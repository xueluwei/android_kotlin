package com.example.xlwapp.receiver

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.xlwapp.utils.eggtimer.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService(){
    override fun onNewToken(token: String) {
        Log.e("testfcm", "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e("testfcm", "From: ${remoteMessage?.from}")
        remoteMessage.notification?.let {
            sendNotification(it.body as String)
            Log.e("testfcm", "Message data payload: ${remoteMessage.data}")
        }
    }

    private fun sendNotification(msg: String) {
        val notificationManager = ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(msg, applicationContext)
    }
}
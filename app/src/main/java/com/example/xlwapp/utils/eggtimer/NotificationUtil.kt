package com.example.xlwapp.utils.eggtimer

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.SystemClock
import android.text.format.DateUtils
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import com.example.xlwapp.R
import com.example.xlwapp.activity.NotificationActivity
import com.example.xlwapp.receiver.AlarmReceiver
import com.example.xlwapp.receiver.SnoozeReceiver

//Notification id
// This ID represents the current notification instance
// and is needed for updating or canceling this notification.
// Since your app will only have one active notification at a given time,
// you can use the same ID for all your notifications.
val NOTIFICATION_ID = 0
val REQUEST_CODE = 0


/**
 * Builds and delivers the notification
 * extension function to send message (GIVEN)
 * */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context){
    //step1: create content intent which is used for you clicking the notification
    //start the NotificationActivity
    val contentIntent = Intent(applicationContext, NotificationActivity::class.java)

    // You created the intent, but the notification is displayed outside your app.
    // To make an intent work outside your app, you need to create a new PendingIntent.
    //step2: create pending intent and use the "contentIntent" which you created
    val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            REQUEST_CODE,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    //step3: create snooze intent which is used for you clicking snooze
    //start the snooze receiver
    val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)

    //step4: create pending intent and use the "snoozeIntent" which you created
    val snoozePendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            REQUEST_CODE,
            snoozeIntent,
            PendingIntent.FLAG_ONE_SHOT //means intent can be used only once, but seems doesn't work
    )

    //step5: add image and style
    val eggImage = BitmapFactory.decodeResource(//open .png use bitmap can scale it
            applicationContext.resources,
            R.drawable.cooked_egg
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(eggImage)
            .bigLargeIcon(null)

    //step6: build the notification use you created earlier
    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.egg_notification_channel_id)
    )
            //set small icon
            .setSmallIcon(R.drawable.cooked_egg)
            //set title
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            //set text (which you passed in)
            .setContentText(messageBody)
            //set style
            .setStyle(bigPicStyle)
            //set large icon
            .setLargeIcon(eggImage)
            //set content intent
            .setContentIntent(contentPendingIntent)
            //set auto cancel while use click the notification
            .setAutoCancel(true)
            //add snooze action
            .addAction(
                    R.drawable.egg_icon,
                    applicationContext.getString(R.string.snooze),
                    snoozePendingIntent
            )
            //set priority
            .setPriority(NotificationCompat.PRIORITY_HIGH)

    //step7: call notify
    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Snooze notification
 * */
fun NotificationManager.snoozeNotification(context: Context){
    //step1: create intent
    val notifyIntent = Intent(context, AlarmReceiver::class.java)

    //step2: create pending intent (update current)
    val notifyPendingInt = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    //step3: get trigger time
    val triggerTime = SystemClock.elapsedRealtime() + DateUtils.MINUTE_IN_MILLIS

    //step4: create alarm manager
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    //set alarm manager compat which can work in low-power idle modes
    //and trigger pending intent in time
    AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            notifyPendingInt
    )

    //step5: cancel current notification
    cancelNotifications()
}

/**
 * Cancel all notifications
 * */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}
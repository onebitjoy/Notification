package com.abhishek.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


class MyApp : Application() {
    // To create notifications channel(categories of notifications)

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // We only need to create channels for android versions later than OREO
            val channel = NotificationChannel(
                "destiny_notification_channel",
                "Destiny",
                NotificationManager.IMPORTANCE_DEFAULT
            )
//            channel.description()
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
}
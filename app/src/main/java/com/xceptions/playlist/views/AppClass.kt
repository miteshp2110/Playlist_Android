package com.xceptions.playlist.views

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class AppClass: Application() {
    override fun onCreate() {
        super.onCreate()
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){
//            val name = "App events"
//            val descriptionText = "Notification for Anything"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel("APP_EVENTS",name,importance).apply {
//                description=descriptionText
//            }
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as
//                        NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }


    }
}
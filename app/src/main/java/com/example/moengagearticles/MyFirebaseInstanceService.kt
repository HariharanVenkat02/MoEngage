package com.example.moengagearticles

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.moengagearticles.UI.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseInstanceService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Check if the message contains data payload
        message.data.isNotEmpty().let {
            // Retrieve data payload
            val dataPayload = message.data

            // Process the data payload or perform any specific actions here
            // In this example, we'll show a notification
            showNotification(dataPayload)
        }
    }

    private fun showNotification(dataPayload: Map<String, String>) {
        val channelId = "MyNotificationChannel"

        // Create an explicit intent for the MainActivity
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // Create a PendingIntent for the intent
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create a notification builder
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(dataPayload["title"])
            .setContentText(dataPayload["message"])
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Check if the Android version is Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel for Oreo and higher
            val channel = NotificationChannel(
                channelId,
                "My Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // Get the notification manager and create the channel
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Get the notification manager and show the notification
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}
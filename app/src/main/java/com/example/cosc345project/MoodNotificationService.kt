package com.example.cosc345project

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

class MoodNotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification() {
        val builder = NotificationCompat.Builder(context, MOOD_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_favorite_border_24)
            .setContentTitle("Mood")
            .setContentText("How was your day today?")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(1, builder.build())
    }

    companion object {
        const val MOOD_CHANNEL_ID = "mood_channel"
    }
}
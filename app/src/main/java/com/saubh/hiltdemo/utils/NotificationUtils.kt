package com.saubh.hiltdemo.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.saubh.hiltdemo.MainActivity
import com.saubh.hiltdemo.R // Assuming you have a R.drawable.ic_notification

object NotificationUtils {

    private const val CHANNEL_ID_SCREEN_1 = "screen_1_channel"
    private const val CHANNEL_ID_SCREEN_2 = "screen_2_channel"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channel1 = NotificationChannel(
                CHANNEL_ID_SCREEN_1,
                "Screen 1 Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications that navigate to Screen 1"
            }
            notificationManager.createNotificationChannel(channel1)

            val channel2 = NotificationChannel(
                CHANNEL_ID_SCREEN_2,
                "Screen 2 Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications that navigate to Screen 2"
            }
            notificationManager.createNotificationChannel(channel2)
        }
    }

    fun showNotification(context: Context, targetScreen: String, notificationId: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("NAVIGATE_TO", targetScreen) // Custom extra to indicate destination
        }

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, notificationId, intent, pendingIntentFlags)

        val channelId = if (targetScreen == "screen1") CHANNEL_ID_SCREEN_1 else CHANNEL_ID_SCREEN_2

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your notification icon
            .setContentTitle("New Content Available")
            .setContentText("Tap to view $targetScreen")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Ensure the app is not in the foreground before showing the notification
        // This logic is tricky to get right here. A more robust solution might involve a Service
        // or checking Activity lifecycle. For simplicity, we'll rely on the MainActivity
        // to decide if navigation should occur from the intent.
        notificationManager.notify(notificationId, builder.build())
    }
}

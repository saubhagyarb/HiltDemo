package com.saubh.hiltdemo.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.saubh.hiltdemo.MainActivity
import com.saubh.hiltdemo.R

object NotificationUtils {
    private const val CHANNEL_ID = "default_channel"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Default notification channel"
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(context: Context, screen: String, notificationId: Int) {
        val instanceId = System.currentTimeMillis().toString()
        val (deepLinkUri, title, text) = when(screen) {
            "screen1" -> Triple(
                "hiltdemo://screen1?instanceId=$instanceId&showCard=false",
                "Screen 1",
                "Open Screen 1 without card"
            )
            "screen1_with_card" -> Triple(
                "hiltdemo://screen1?instanceId=$instanceId&showCard=true",
                "Screen 1 with Card",
                "Open Screen 1 with card view"
            )
            "screen2" -> Triple(
                "hiltdemo://screen2",
                "Screen 2",
                "Open Screen 2"
            )
            else -> return
        }

        val intent = Intent(
            Intent.ACTION_VIEW,
            deepLinkUri.toUri(),
            context,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
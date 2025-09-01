package com.saubh.hiltdemo

import android.app.Application
import com.saubh.hiltdemo.utils.NotificationUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationUtils.createNotificationChannels(this)
    }
}
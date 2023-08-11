package com.emikhalets.fitness.presentation

import android.app.Application
import com.emikhalets.simpleevents.utils.AppNotificationManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppNotificationManager.createNotificationChannels(applicationContext)
    }
}

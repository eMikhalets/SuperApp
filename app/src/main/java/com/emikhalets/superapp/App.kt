package com.emikhalets.superapp

import android.app.Application
import com.emikhalets.core.common.logi
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        logi(TAG, "onCreate()")
    }

    companion object {

        private const val TAG = "App"
    }
}

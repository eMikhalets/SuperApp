package com.emikhalets.common

import android.util.Log

fun logd(tag: String, text: String) = AppLogger.logd(tag, text)

fun logi(tag: String, text: String) = AppLogger.logi(tag, text)

fun loge(tag: String, text: String, throwable: Throwable? = null) =
    AppLogger.loge(tag, text, throwable)

object AppLogger {

    private var isLogsEnabled: Boolean = false

    fun init() {
        isLogsEnabled = true
    }

    fun logd(tag: String, text: String) {
        if (isLogsEnabled) Log.d(tag, text)
    }

    fun logi(tag: String, text: String) {
        if (isLogsEnabled) Log.i(tag, text)
    }

    fun loge(tag: String, text: String, throwable: Throwable? = null) {
        if (isLogsEnabled) Log.e(tag, text, throwable)
    }
}

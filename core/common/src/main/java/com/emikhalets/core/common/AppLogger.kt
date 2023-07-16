package com.emikhalets.core.common

import android.util.Log

fun logd(tag: String, text: String?) {
    text?.let { AppLogger.logd(tag, text) }
}

fun logi(tag: String, text: String?) {
    text?.let { AppLogger.logi(tag, text) }
}

fun loge(tag: String, text: String?, throwable: Throwable? = null) {
    text?.let { AppLogger.loge(tag, text, throwable) }
}

fun loge(tag: String, throwable: Throwable? = null) {
    AppLogger.loge(tag, throwable)
}

object AppLogger {

    private var isLogsEnabled: Boolean = false

    fun init() {
        isLogsEnabled = true
    }

    internal fun logd(tag: String, text: String) {
        if (isLogsEnabled) Log.d(tag, text)
    }

    internal fun logi(tag: String, text: String) {
        if (isLogsEnabled) Log.i(tag, text)
    }

    internal fun loge(tag: String, text: String, throwable: Throwable? = null) {
        if (isLogsEnabled) Log.e(tag, text, throwable)
    }

    internal fun loge(tag: String, throwable: Throwable? = null) {
        if (isLogsEnabled) Log.e(tag, "", throwable)
    }
}

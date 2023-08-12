package com.emikhalets.core.common.extensions

import timber.log.Timber

fun Any.logd(message: String) {
    Timber.tag(this::class.java.simpleName).d(message)
}

fun Any.loge(message: String) {
    Timber.tag(this::class.java.simpleName).e(message)
}

fun Any.loge(throwable: Throwable) {
    Timber.tag(this::class.java.simpleName).e(throwable)
}
package com.emikhalets.core.common.mvi

import com.emikhalets.core.common.loge

fun String.toDoubleOrZero(): Double {
    return try {
        toDouble()
    } catch (e: NumberFormatException) {
        loge("toDoubleOrZero", e)
        0.0
    }
}
package com.emikhalets.core.common

fun String.toIntOrNull(): Int? {
    return try {
        toInt()
    } catch (e: NumberFormatException) {
        null
    }
}
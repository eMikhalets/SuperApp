package com.emikhalets.core.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long?.formatWithPattern(pattern: String): String {
    val date = this?.let { Date(it) } ?: Date().time
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}
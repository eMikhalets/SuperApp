package com.emikhalets.core.common.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long?.formatWithPattern(pattern: String): String {
    val date = this?.let { Date(it) } ?: Date().time
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}

fun Long?.formatFullDate(): String {
    val date = this?.let { Date(it) } ?: Date().time
    return SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(date)
}

fun Long?.formatMidDate(): String {
    val date = this?.let { Date(it) } ?: Date().time
    return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
}

fun Long?.formatShortDate(): String {
    val date = this?.let { Date(it) } ?: Date().time
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
}
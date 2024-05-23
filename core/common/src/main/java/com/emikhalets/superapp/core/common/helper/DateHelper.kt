package com.emikhalets.superapp.core.common.helper

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateHelper {

    val now: Long
        get() = Calendar.getInstance().timeInMillis

    fun formatDate(timestamp: Long?, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(timestamp ?: now)
    }

    fun timestampOf(day: Int, month: Int, year: Int): Long {
        if (day !in 1..31) return now
        if (month !in 1..12) return now
        return Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.MONTH, month - 1)
            set(Calendar.YEAR, year)
        }.timeInMillis
    }
}
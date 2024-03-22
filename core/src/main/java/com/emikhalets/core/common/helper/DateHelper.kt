package com.emikhalets.core.common.helper

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateHelper {

    val nowTimestamp: Long
        get() = Calendar.getInstance().timeInMillis

    fun formatDate(timestamp: Long?, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(timestamp ?: nowTimestamp)
    }

    fun timestampOf(day: Int, month: Int, year: Int): Long {
        if (day !in 1..31) return nowTimestamp
        if (month !in 0..11) return nowTimestamp
        return Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
        }.timeInMillis
    }
}
package com.emikhalets.superapp.core.common.date

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateHelper {

    val now: Long
        get() = Calendar.getInstance().timeInMillis

    @Deprecated("")
    fun formatDate(timestamp: Long?, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(timestamp ?: now)
    }

    fun format(pattern: String, value: Long): String? {
        return try {
            SimpleDateFormat(pattern, Locale.getDefault()).format(value)
        } catch (e: Exception) {
            Timber.d(e)
            null
        }
    }

    /**
     * Return timestamp of date
     * @param day values 1 - 31
     * @param month values 1 - 12
     * @param year any values
     */
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
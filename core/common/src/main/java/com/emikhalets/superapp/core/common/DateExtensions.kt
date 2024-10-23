package com.emikhalets.superapp.core.common

import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

internal val timezone = ZoneId.systemDefault()

// Format

/**
 * Форматирование timestamp даты по заданному паттерну. Возвращает null в случае ошибки
 */
fun Long?.format(pattern: String): String? {
    return try {
        SimpleDateFormat(pattern, Locale.getDefault()).format(this ?: Date().time)
    } catch (e: Exception) {
        Timber.d(e)
        null
    }
}

// LocalDate

fun Long?.localDate(): LocalDate {
    val instant = Instant.ofEpochMilli(this ?: Date().time)
    return LocalDateTime.ofInstant(instant, timezone).toLocalDate()
}

fun Long?.localDateTime(): LocalDateTime {
    val instant = Instant.ofEpochMilli(this ?: Date().time)
    return LocalDateTime.ofInstant(instant, timezone)
}

// Timestamp

fun timestamp(): Long {
    return Calendar.getInstance().timeInMillis
}

/**
 * Return timestamp of date
 * @param day values 1 - 31
 * @param month values 1 - 12
 * @param year any values
 */
fun timestampOf(day: Int, month: Int, year: Int): Long {
    if (day !in 1..31) return timestamp()
    if (month !in 1..12) return timestamp()
    return Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.MONTH, month - 1)
        set(Calendar.YEAR, year)
    }.timeInMillis
}

fun LocalDate.timestamp(zone: ZoneId = timezone): Long {
    return atStartOfDay(zone).toInstant().toEpochMilli()
}

fun LocalDateTime.timestamp(zone: ZoneId = timezone): Long {
    return atZone(zone).toInstant().toEpochMilli()
}

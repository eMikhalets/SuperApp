package com.emikhalets.common

import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

object DateHelper {

    private val timezone = ZoneId.systemDefault()

    fun localDate(timestamp: Long = Date().time): LocalDate {
        val instant = Instant.ofEpochMilli(timestamp)
        return LocalDateTime.ofInstant(instant, timezone).toLocalDate()
    }

    fun localDateTime(timestamp: Long = Date().time): LocalDateTime {
        val instant = Instant.ofEpochMilli(timestamp)
        return LocalDateTime.ofInstant(instant, timezone)
    }

    fun timestamp(localDate: LocalDate, zone: ZoneId = timezone): Long {
        return localDate.atStartOfDay(zone).toInstant().toEpochMilli()
    }

    fun timestamp(localDateTime: LocalDateTime, zone: ZoneId = timezone): Long {
        return localDateTime.atZone(zone).toInstant().toEpochMilli()
    }

    fun formatDate(timestamp: Long, pattern: String): String {
        return getFormatter(pattern).format(Date(timestamp))
    }

    fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }

    fun getSevenDaysTimestamps(): List<Long> {
        val now = localDate()
        return buildList {
            repeat(7) {
                add(timestamp(now.plusDays(it.toLong())))
            }
        }
    }

    private fun getFormatter(
        pattern: String,
        locale: Locale = Locale.getDefault(),
    ): SimpleDateFormat {
        return SimpleDateFormat(pattern, locale)
    }
}

package com.emikhalets.common

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.ZoneId
import java.time.temporal.ChronoUnit
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


    // End and Start of dates

    fun Long.getMonthEdges(): Pair<Long, Long> {
        val date = this.localDate
        val start = date.withDayOfMonth(1).milliseconds
        val days = date.lengthOfMonth()
        val end = date.withDayOfMonth(days).milliseconds
        return Pair(start, end)
    }

    fun Long.getYearEdges(): Pair<Long, Long> {
        val date = this.localDate
        val start = date.withDayOfYear(1).milliseconds
        val days = date.lengthOfYear()
        val end = date.withDayOfYear(days).milliseconds
        return Pair(start, end)
    }

    // Date formatting

    fun formatTime(hour: Int, minute: Int): String {
        val minuteString = if (minute < 10) "0$minute" else "$minute"
        return "$hour:$minuteString"
    }

    fun Long.formatDateFull(withoutYear: Boolean): String {
        val pattern = if (withoutYear) "EEEE, d MMM" else "EEEE, d MMM, yyyy"
        return SimpleDateFormat(pattern, Locale.ENGLISH).format(this)
    }

    fun Long.formatDateMonth(): String =
        SimpleDateFormat("MMMM", Locale.ENGLISH).format(this)

    fun Long.formatDate(pattern: String = "dd MM yyyy"): String =
        SimpleDateFormat(pattern, Locale.getDefault()).format(this)

    fun Long.formatDateThisYear(pattern: String): String {
        val date = this.localDate.withYear(LocalDate.now().year).milliseconds
        return SimpleDateFormat(pattern, Locale.ENGLISH).format(date)
    }

// Converters

    val LocalDate.milliseconds: Long
        get() = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    val Long.localDate: LocalDate
        get() = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

    // Calculations

    fun turns(timestamp: Long): Int {
        val turns = ChronoUnit.YEARS.between(localDate(timestamp), LocalDate.now()).toInt() + 1
        return if (turns < 0) 0 else turns
    }

    fun daysLeft(timestamp: Long): Int {
        var left = ChronoUnit.DAYS
            .between(LocalDate.now(), localDate(timestamp).withYear(LocalDate.now().year))
            .toInt()
        if (left < 0) left += Year.now().length()
        return left
    }
}

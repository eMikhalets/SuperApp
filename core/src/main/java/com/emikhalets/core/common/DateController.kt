package com.emikhalets.core.common

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

class DateController @Inject constructor() {

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
}

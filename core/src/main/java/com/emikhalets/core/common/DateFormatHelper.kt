package com.emikhalets.core.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatHelper {

    fun withPattern(timestamp: Long, pattern: String): String {
        return getFormatter(pattern).format(Date(timestamp))
    }

    private fun getFormatter(
        pattern: String,
        locale: Locale = Locale.getDefault(),
    ): SimpleDateFormat = SimpleDateFormat(pattern, locale)
}

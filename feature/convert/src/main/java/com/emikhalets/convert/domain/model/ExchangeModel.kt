package com.emikhalets.convert.domain.model

import com.emikhalets.core.common.LongZero
import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import java.util.Date

data class ExchangeModel(
    val id: Long,
    val main: String,
    val sub: String,
    val value: Double,
    val date: Long,
) {

    val fullCode: String
        get() = "$main$sub"

    constructor(main: String) : this(0, main, "", 0.0, 0)

    constructor(main: String, sub: String) : this(0, main, sub, 0.0, 0)

    fun containsPair(base: String, currency: String): Boolean {
        return (main == base && sub == currency) || (main == currency && sub == base)
    }

    fun calculate(base: String, value: Long): Long {
        return when (base) {
            main -> ((value / 100) * this.value).toLong()
            sub -> ((value / 100) * (1 / this.value)).toLong()
            else -> 0
        }
    }

    fun isNeedUpdate(): Boolean {
        if (sub.isEmpty()) return false
        if (date == LongZero) return true
        val startOfNextDay = date
            .localDate()
            .atStartOfDay()
            .plusDays(1)
            .timestamp()
        return startOfNextDay < Date().time
    }
}

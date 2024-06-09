package com.emikhalets.superapp.domain.convert

import com.emikhalets.superapp.core.common.date.localDate
import com.emikhalets.superapp.core.common.date.timestamp
import java.util.Date

data class CurrencyPairModel(
    val id: Long,
    val mainCode: String,
    val subCode: String,
    val value: Double,
    val date: Long,
) {

    val code: String
        get() = "$mainCode$subCode"

    constructor(main: String) : this(0, main, "", 0.0, 0)

    constructor(main: String, sub: String) : this(0, main, sub, 0.0, 0)

    fun containsPair(base: String, currency: String): Boolean {
        return (mainCode == base && subCode == currency) ||
                (mainCode == currency && subCode == base)
    }

    fun calculate(base: String, value: Long): Long {
        return when (base) {
            mainCode -> ((value / 100) * this.value).toLong()
            subCode -> ((value / 100) * (1 / this.value)).toLong()
            else -> 0
        }
    }

    fun isNeedUpdate(): Boolean {
        if (subCode.isEmpty()) return false
        if (date == 0L) return true
        val startOfNextDay = date
            .localDate()
            .atStartOfDay()
            .plusDays(1)
            .timestamp()
        return startOfNextDay < Date().time
    }
}

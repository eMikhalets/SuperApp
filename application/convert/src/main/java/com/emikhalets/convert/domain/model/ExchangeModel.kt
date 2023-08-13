package com.emikhalets.convert.domain.model

import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import java.util.Date

data class ExchangeModel(
    val id: Long,
    val code: String,
    val value: Double,
    val date: Long,
) {

    val main: String get() = code.take(3)
    val secondary: String get() = code.takeLast(3)

    constructor(code: String) : this(0, code, 0.0, 0)

    constructor(main: String, sub: String) : this(0, "$main$sub", 0.0, 0)

    fun withValue(value: Double?, date: Long): ExchangeModel {
        return value?.let { this.copy(value = value, date = date) } ?: this
    }

    fun addCode(code: String): ExchangeModel {
        return this.copy(code = "${this.code}$code")
    }

    fun hasOldValue(): Boolean {
        val startOfNextDay = date
            .localDate()
            .atStartOfDay()
            .plusDays(1)
            .timestamp()
        return startOfNextDay < Date().time
    }

    fun containsPair(base: String, currency: String): Boolean {
        return (main == base && secondary == currency) || (main == currency && secondary == base)
    }

    fun calculate(base: String, value: Long): Long {
        return when (base) {
            main -> (value * this.value).toLong()
            secondary -> (value * (1 / this.value)).toLong()
            else -> 0
        }
    }
}

fun List<ExchangeModel>.hasOldValues(): Boolean {
    return any { item -> (item.code.count() <= 3) || item.hasOldValue() }
}

fun List<ExchangeModel>.filterNeedUpdate(): List<ExchangeModel> {
    return filter { item -> (item.code.count() > 3) && item.hasOldValue() }
}

package com.emikhalets.convert.domain.entity

import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import java.util.Date

data class ExchangeEntity(
    val id: Long,
    val code: String,
    val value: Double,
    val date: Long,
) {

    val main: String get() = code.take(3)
    val secondary: String get() = code.takeLast(3)

    constructor(code: String) : this(0, code, 0.0, 0)

    constructor(main: String, sub: String) : this(0, "$main$sub", 0.0, 0)

    fun withValue(value: Double?, date: Long): ExchangeEntity {
        return value?.let { this.copy(value = value, date = date) } ?: this
    }

    fun addCode(code: String): ExchangeEntity {
        return this.copy(code = "${this.code}$code")
    }

    fun isOldValue(): Boolean {
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

    fun calculate(base: String, value: Double): Double {
        return when (base) {
            main -> value * this.value
            secondary -> value * (1 / this.value)
            else -> 0.0
        }
    }
}

fun List<ExchangeEntity>.filterNeedUpdate(): List<ExchangeEntity> {
    return filter { item -> (item.code.count() > 3) && item.isOldValue() }
}

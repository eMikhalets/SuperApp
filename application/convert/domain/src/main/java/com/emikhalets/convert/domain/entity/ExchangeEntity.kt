package com.emikhalets.convert.domain.entity

import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import java.util.Date

data class ExchangeEntity(
    val id: Long,
    val mainCurrency: String,
    val secondaryCurrency: String,
    val value: Double,
    val date: Long,
) {

    constructor(mainCurrency: String, secondaryCurrency: String) : this(
        id = 0,
        mainCurrency = mainCurrency,
        secondaryCurrency = secondaryCurrency,
        value = 0.0,
        date = Date().time,
    )

    fun withValue(value: Double): ExchangeEntity {
        return this.copy(value = value)
    }

    fun getRequestCode(): String {
        if (secondaryCurrency.isBlank()) return ""
        return "$mainCurrency$secondaryCurrency"
    }

    fun isOldValue(): Boolean {
        val startOfNextDay = date
            .localDate()
            .atStartOfDay()
            .plusDays(1)
            .timestamp()
        return startOfNextDay >= Date().time
    }

    fun isCurrency(base: String, currency: String): Boolean {
        return mainCurrency == base && secondaryCurrency == currency
                || mainCurrency == currency && secondaryCurrency == base
    }
}

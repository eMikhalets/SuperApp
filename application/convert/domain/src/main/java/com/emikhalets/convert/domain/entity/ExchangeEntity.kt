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
        value = -1.0,
        date = Date().time,
    )

    fun withValue(value: Double): ExchangeEntity {
        return this.copy(value = value)
    }

    fun getExchangeCodes(): Pair<String, String> {
        return Pair("$mainCurrency$secondaryCurrency", "$secondaryCurrency$mainCurrency")
    }

    fun isOldValue(): Boolean {
        val endOfDay = date.localDate().atStartOfDay().plusDays(1).timestamp()
        return endOfDay < Date().time
    }
}

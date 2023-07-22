package com.emikhalets.feature.currencies_convert.domain.model

data class CurrencyModel(
    val id: Long,
    val code: String,
) {

    constructor(code: String) : this(0, code)
}

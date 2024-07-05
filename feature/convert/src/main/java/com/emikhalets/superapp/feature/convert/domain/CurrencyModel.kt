package com.emikhalets.superapp.feature.convert.domain

data class CurrencyModel(
    val id: Long,
    val code: String,
) {

    constructor(code: String) : this(0, code)
}

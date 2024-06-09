package com.emikhalets.superapp.domain.convert

data class CurrencyModel(
    val id: Long,
    val code: String,
) {

    constructor(code: String) : this(0, code)
}

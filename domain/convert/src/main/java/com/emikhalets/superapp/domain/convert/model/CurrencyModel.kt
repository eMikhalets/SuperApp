package com.emikhalets.superapp.domain.convert.model

data class CurrencyModel(
    val id: Long,
    val code: String,
) {

    constructor(code: String) : this(0, code)
}

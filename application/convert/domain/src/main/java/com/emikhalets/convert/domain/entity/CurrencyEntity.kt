package com.emikhalets.convert.domain.entity

data class CurrencyEntity(
    val id: Long,
    val code: String,
) {

    constructor(code: String) : this(0, code)
}

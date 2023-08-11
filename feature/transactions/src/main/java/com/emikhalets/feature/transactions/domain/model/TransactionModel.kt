package com.emikhalets.feature.transactions.domain.model

import java.util.Date

data class TransactionModel(
    val id: Long,
    val value: Long,
    val type: TransactionType,
    val note: String,
    val timestamp: Long,
) {

    constructor(value: Long, type: TransactionType) : this(0, value, type, "", Date().time)
}

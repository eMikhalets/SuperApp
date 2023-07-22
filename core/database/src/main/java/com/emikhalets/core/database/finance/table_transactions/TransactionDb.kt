package com.emikhalets.core.database.finance.table_transactions

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity("transactions")
data class TransactionDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "wallet_id") val walletId: Long,
    @ColumnInfo(name = "currency_id") val currencyId: Long,
    @ColumnInfo(name = "value") val value: Long,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "note") val note: String,
    @ColumnInfo(name = "exchange_value") val exchangeValue: Double,
)
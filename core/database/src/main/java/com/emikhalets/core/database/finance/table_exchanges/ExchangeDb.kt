package com.emikhalets.core.database.finance.table_exchanges

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchanges")
data class ExchangeDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "currency_main_id") val currencyMainId: Long,
    @ColumnInfo(name = "currency_sub_id") val currencySubId: Long,
    @ColumnInfo(name = "value") val value: Double,
    @ColumnInfo(name = "date") val date: Long,
)

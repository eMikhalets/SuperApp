package com.emikhalets.superapp.core.database.convert.table_currency_pair

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_pair")
data class CurrencyPairDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "main")
    val main: String,
    @ColumnInfo(name = "sub")
    val sub: String,
    @ColumnInfo(name = "value")
    val value: Double,
    @ColumnInfo(name = "date")
    val date: Long,
)

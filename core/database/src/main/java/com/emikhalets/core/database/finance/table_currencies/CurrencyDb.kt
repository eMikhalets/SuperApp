package com.emikhalets.core.database.finance.table_currencies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "name") val name: String,
)

package com.emikhalets.core.database.finance.table_convert_currencies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "convert_currencies")
data class ConvertCurrencyDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "code") val code: String,
)

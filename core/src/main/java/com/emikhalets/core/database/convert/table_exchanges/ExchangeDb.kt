package com.emikhalets.core.database.convert.table_exchanges

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchanges")
data class ExchangeDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "value") val value: Double,
    @ColumnInfo(name = "date") val date: Long,
)
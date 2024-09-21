package com.emikhalets.superapp.core.database.convert.table_exchanges

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchanges")
data class ExchangeDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "main_code")
    val mainCode: String,
    @ColumnInfo(name = "sub_code")
    val subCode: String,
    @ColumnInfo(name = "value")
    val value: Double,
    @ColumnInfo(name = "update_date")
    val updateDate: Long,
)

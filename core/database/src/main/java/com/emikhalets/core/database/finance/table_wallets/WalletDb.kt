package com.emikhalets.core.database.finance.table_wallets

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("wallets")
data class WalletDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "currency_id") val currencyId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "initial_value") val initialValue: Long,
)
package com.emikhalets.core.database.finance.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.emikhalets.core.database.finance.table_currencies.CurrencyDb
import com.emikhalets.core.database.finance.table_transactions.TransactionDb
import com.emikhalets.core.database.finance.table_wallets.WalletDb

data class WalletFullDb(
    @Embedded
    val wallet: WalletDb,
    @Relation(parentColumn = "id", entityColumn = "wallet_id")
    val transactions: List<TransactionDb>,
    @Relation(parentColumn = "currency_id", entityColumn = "id")
    val currency: CurrencyDb,
)
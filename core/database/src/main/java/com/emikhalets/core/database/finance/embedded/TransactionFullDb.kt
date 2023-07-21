package com.emikhalets.core.database.finance.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.emikhalets.core.database.finance.table_category.CategoryDb
import com.emikhalets.core.database.finance.table_currencies.CurrencyDb
import com.emikhalets.core.database.finance.table_transactions.TransactionDb
import com.emikhalets.core.database.finance.table_wallets.WalletDb

data class TransactionFullDb(
    @Embedded
    val transaction: TransactionDb,
    @Relation(parentColumn = "category_id", entityColumn = "id")
    val category: CategoryDb,
    @Relation(parentColumn = "currency_id", entityColumn = "id")
    val currency: CurrencyDb,
    @Relation(parentColumn = "wallet_id", entityColumn = "id")
    val wallet: WalletDb,
)
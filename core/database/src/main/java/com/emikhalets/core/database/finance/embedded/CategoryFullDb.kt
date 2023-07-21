package com.emikhalets.core.database.finance.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.emikhalets.core.database.finance.table_category.CategoryDb
import com.emikhalets.core.database.finance.table_transactions.TransactionDb

data class CategoryFullDb(
    @Embedded
    val category: CategoryDb,
    @Relation(parentColumn = "id", entityColumn = "category_id")
    val transactions: List<TransactionDb>,
)
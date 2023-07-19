package com.emikhalets.core.database.finance.table_transactions

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.emikhalets.core.database.AppDao
import com.emikhalets.core.database.finance.embedded.TransactionFullDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao : AppDao<TransactionDb> {

    @Query("DELETE FROM transactions")
    suspend fun drop()

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionDb>

    @Query("SELECT * FROM transactions")
    fun getAllFlow(): Flow<List<TransactionDb>>

    @Transaction
    @Query("SELECT * FROM transactions")
    suspend fun getAllFullFlow(): Flow<List<TransactionFullDb>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getItem(id: Long): TransactionDb

    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getItemFlow(id: Long): Flow<TransactionDb>

    @Query("SELECT * FROM transactions WHERE category_id=:id")
    suspend fun getAllByCategory(id: Long): List<TransactionDb>

    @Query("SELECT * FROM transactions WHERE wallet_id=:id")
    suspend fun getAllByWallet(id: Long): List<TransactionDb>
}
package com.emikhalets.core.database.finance.table_wallets

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.emikhalets.core.database.AppDao
import com.emikhalets.core.database.finance.embedded.WalletFullDb
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletsDao : AppDao<WalletDb> {

    @Query("DELETE FROM wallets")
    suspend fun drop()

    @Query("SELECT * FROM wallets")
    suspend fun getAll(): List<WalletDb>

    @Query("SELECT * FROM wallets")
    fun getAllFlow(): Flow<List<WalletDb>>

    @Transaction
    @Query("SELECT * FROM wallets")
    fun getAllFullFlow(): Flow<List<WalletFullDb>>

    @Query("SELECT * FROM wallets WHERE id = :id")
    suspend fun getItem(id: Long): WalletDb

    @Query("SELECT * FROM wallets WHERE id = :id")
    fun getItemFlow(id: Long): Flow<WalletDb>

    @Query("SELECT EXISTS(SELECT * FROM wallets WHERE name=:name)")
    suspend fun isExists(name: String): Boolean
}
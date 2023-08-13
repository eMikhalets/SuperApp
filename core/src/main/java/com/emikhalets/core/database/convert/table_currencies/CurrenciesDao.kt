package com.emikhalets.core.database.convert.table_currencies

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesDao : AppDao<CurrencyDb> {

    @Query("DELETE FROM currencies")
    suspend fun drop(): Int

    @Query("SELECT * FROM currencies")
    suspend fun getAll(): List<CurrencyDb>

    @Query("SELECT * FROM currencies")
    fun getAllFlow(): Flow<List<CurrencyDb>>

    @Query("SELECT * FROM currencies WHERE id = :id")
    suspend fun getItem(id: Long): CurrencyDb

    @Query("SELECT * FROM currencies WHERE id = :id")
    fun getItemFlow(id: Long): Flow<CurrencyDb>

    @Query("SELECT EXISTS(SELECT * FROM currencies WHERE code = :code)")
    suspend fun isCodeExist(code: String): Boolean

    @Query("DELETE FROM currencies WHERE code == :code")
    suspend fun deleteByCode(code: String)
}
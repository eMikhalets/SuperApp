package com.emikhalets.core.database.finance.table_convert_currencies

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ConvertCurrenciesDao : AppDao<ConvertCurrencyDb> {

    @Query("DELETE FROM convert_currencies")
    suspend fun drop()

    @Query("SELECT * FROM convert_currencies")
    suspend fun getAll(): List<ConvertCurrencyDb>

    @Query("SELECT * FROM convert_currencies")
    fun getAllFlow(): Flow<List<ConvertCurrencyDb>>

    @Query("SELECT * FROM convert_currencies WHERE id = :id")
    suspend fun getItem(id: Long): ConvertCurrencyDb

    @Query("SELECT * FROM convert_currencies WHERE id = :id")
    fun getItemFlow(id: Long): Flow<ConvertCurrencyDb>

    @Query("SELECT EXISTS(SELECT * FROM convert_currencies WHERE code = :code)")
    suspend fun isCodeExist(code: String): Boolean

    @Query("DELETE FROM convert_currencies WHERE code == :code")
    suspend fun deleteByCode(code: String)
}
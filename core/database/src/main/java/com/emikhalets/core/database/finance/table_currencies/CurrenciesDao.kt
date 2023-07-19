package com.emikhalets.core.database.finance.table_currencies

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesDao : AppDao<CurrencyDb> {

    @Query("DELETE FROM currencies")
    suspend fun drop()

    @Query("SELECT EXISTS(SELECT * FROM currencies WHERE code = :code)")
    suspend fun isExist(code: String): Boolean

    @Query("SELECT * FROM currencies")
    suspend fun getAll(): List<CurrencyDb>

    @Query("SELECT * FROM currencies")
    fun getAllFlow(): Flow<List<CurrencyDb>>
}
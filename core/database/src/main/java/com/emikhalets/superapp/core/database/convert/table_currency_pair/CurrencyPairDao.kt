package com.emikhalets.superapp.core.database.convert.table_currency_pair

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.superapp.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyPairDao : AppDao<CurrencyPairDb> {

    @Query("DELETE FROM currency_pair")
    suspend fun drop()

    @Query("SELECT * FROM currency_pair")
    suspend fun getAll(): List<CurrencyPairDb>

    @Query("SELECT * FROM currency_pair")
    fun getAllFlow(): Flow<List<CurrencyPairDb>>

    @Query("SELECT * FROM currency_pair WHERE id = :id")
    suspend fun getItem(id: Long): CurrencyPairDb

    @Query("SELECT * FROM currency_pair WHERE id = :id")
    fun getItemFlow(id: Long): Flow<CurrencyPairDb>

    @Query(
        "DELETE FROM currency_pair " +
                "WHERE main LIKE '%' || :code || '%' OR sub LIKE '%' || :code || '%'"
    )
    suspend fun deleteByCode(code: String)
}
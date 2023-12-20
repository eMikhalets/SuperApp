package com.emikhalets.core.database.convert.table_exchanges

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangesDao : AppDao<ExchangeDb> {

    @Query("DELETE FROM exchanges")
    suspend fun drop()

    @Query("SELECT * FROM exchanges")
    suspend fun getAll(): List<ExchangeDb>

    @Query("SELECT * FROM exchanges")
    fun getAllFlow(): Flow<List<ExchangeDb>>

    @Query("SELECT * FROM exchanges WHERE id = :id")
    suspend fun getItem(id: Long): ExchangeDb

    @Query("SELECT * FROM exchanges WHERE id = :id")
    fun getItemFlow(id: Long): Flow<ExchangeDb>

    @Query("DELETE FROM exchanges WHERE main LIKE '%' || :code || '%' OR sub LIKE '%' || :code || '%'")
    suspend fun deleteByCode(code: String)
}
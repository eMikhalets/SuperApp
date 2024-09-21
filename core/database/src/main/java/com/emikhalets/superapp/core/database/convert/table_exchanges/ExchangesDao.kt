package com.emikhalets.superapp.core.database.convert.table_exchanges

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.superapp.core.database.AppDao
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

    @Query("SELECT EXISTS(SELECT * FROM exchanges WHERE main_code = :code)")
    suspend fun isCodeExist(code: String): Boolean

    @Query("DELETE FROM exchanges WHERE code == :code")
    suspend fun deleteByCode(code: String)
}
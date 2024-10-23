package com.emikhalets.superapp.core.database.convert.table_exchanges

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.superapp.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangesDao : AppDao<ExchangeDb> {

    @Query("SELECT * FROM exchanges")
    fun getAllFlow(): Flow<List<ExchangeDb>>

    @Query("SELECT * FROM exchanges")
    suspend fun getAll(): List<ExchangeDb>

    @Query("SELECT EXISTS(SELECT * FROM exchanges WHERE main_code = :code OR sub_code == :code)")
    suspend fun isCodeExist(code: String): Boolean

    @Query("DELETE FROM exchanges WHERE main_code == :code OR sub_code == :code")
    suspend fun deleteByCode(code: String): Int
}
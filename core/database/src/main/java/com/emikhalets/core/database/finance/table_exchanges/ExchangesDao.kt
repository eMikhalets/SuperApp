package com.emikhalets.core.database.finance.table_exchanges

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao

@Dao
interface ExchangesDao : AppDao<ExchangeDb> {

    @Query("DELETE FROM exchanges")
    suspend fun drop()

    @Query("SELECT * FROM exchanges")
    suspend fun getAll(): List<ExchangeDb>
}
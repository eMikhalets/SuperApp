package com.emikhalets.convert.data.database.table_exchanges

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExchangesDao {

    @Insert
    suspend fun insert(entity: ExchangeDb)

    @Insert
    suspend fun insert(list: List<ExchangeDb>)

    @Update
    suspend fun update(entity: ExchangeDb)

    @Update
    suspend fun update(list: List<ExchangeDb>)

    @Query("DELETE FROM exchanges WHERE code LIKE '%' + :code + '%'")
    suspend fun delete(code: String)

    @Query("SELECT * FROM exchanges")
    suspend fun getAll(): List<ExchangeDb>
}
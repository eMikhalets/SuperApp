package com.emikhalets.convert.data.database.table_exchanges

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangesDao {

    @Insert
    suspend fun insert(entity: ExchangeDb)

    @Insert
    suspend fun insert(list: List<ExchangeDb>)

    @Update
    suspend fun update(entity: ExchangeDb)

    @Query("DELETE FROM exchanges WHERE main = :code OR secondary = :code")
    suspend fun delete(code: String)

    @Query("SELECT * FROM exchanges")
    fun getAllFlow(): Flow<List<ExchangeDb>>
}
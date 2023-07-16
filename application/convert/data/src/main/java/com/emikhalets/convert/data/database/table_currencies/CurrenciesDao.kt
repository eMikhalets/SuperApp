package com.emikhalets.convert.data.database.table_currencies

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesDao {

    @Insert
    suspend fun insert(entity: CurrencyDb)

    @Update
    suspend fun update(entity: CurrencyDb)

    @Delete
    suspend fun delete(entity: CurrencyDb)

    @Query("SELECT EXISTS(SELECT * FROM currencies WHERE code = :code)")
    suspend fun isExist(code: String): Boolean

    @Query("DELETE FROM currencies WHERE code = :code")
    suspend fun delete(code: String)

    @Query("SELECT * FROM currencies")
    suspend fun getAll(): List<CurrencyDb>

    @Query("SELECT * FROM currencies")
    fun getAllFlow(): Flow<List<CurrencyDb>>
}
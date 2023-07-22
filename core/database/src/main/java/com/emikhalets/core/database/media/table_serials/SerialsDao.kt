package com.emikhalets.core.database.media.table_serials

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface SerialsDao : AppDao<SerialDb> {

    @Query("DELETE FROM serials")
    suspend fun drop()

    @Query("SELECT * FROM serials")
    suspend fun getAll(): List<SerialDb>

    @Query("SELECT * FROM serials")
    fun getAllFlow(): Flow<List<SerialDb>>

    @Query("SELECT * FROM serials WHERE id = :id")
    suspend fun getItem(id: Long): SerialDb

    @Query("SELECT * FROM serials WHERE id = :id")
    fun getItemFlow(id: Long): Flow<SerialDb>
}
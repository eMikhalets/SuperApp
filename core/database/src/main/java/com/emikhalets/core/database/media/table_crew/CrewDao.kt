package com.emikhalets.core.database.media.table_crew

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CrewDao : AppDao<CrewDb> {

    @Query("DELETE FROM crew")
    suspend fun drop()

    @Query("SELECT * FROM crew")
    suspend fun getAll(): List<CrewDb>

    @Query("SELECT * FROM crew")
    fun getAllFlow(): Flow<List<CrewDb>>

    @Query("SELECT * FROM crew WHERE id = :id")
    suspend fun getItem(id: Long): CrewDb

    @Query("SELECT * FROM crew WHERE id = :id")
    fun getItemFlow(id: Long): Flow<CrewDb>
}
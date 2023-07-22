package com.emikhalets.core.database.workout.table_programs

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramsDao : AppDao<ProgramDb> {

    @Query("DELETE FROM programs")
    suspend fun drop()

    @Query("SELECT * FROM programs")
    suspend fun getAll(): List<ProgramDb>

    @Query("SELECT * FROM programs")
    fun getAllFlow(): Flow<List<ProgramDb>>

    @Query("SELECT * FROM programs WHERE id = :id")
    suspend fun getItem(id: Long): ProgramDb

    @Query("SELECT * FROM programs WHERE id = :id")
    fun getItemFlow(id: Long): Flow<ProgramDb>
}
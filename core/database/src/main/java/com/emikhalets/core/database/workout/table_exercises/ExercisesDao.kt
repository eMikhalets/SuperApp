package com.emikhalets.core.database.workout.table_exercises

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ExercisesDao : AppDao<ExercisesDb> {

    @Query("DELETE FROM exercises")
    suspend fun drop()

    @Query("SELECT * FROM exercises")
    suspend fun getAll(): List<ExercisesDb>

    @Query("SELECT * FROM exercises")
    fun getAllFlow(): Flow<List<ExercisesDb>>

    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun getItem(id: Long): ExercisesDb

    @Query("SELECT * FROM exercises WHERE id = :id")
    fun getItemFlow(id: Long): Flow<ExercisesDb>
}
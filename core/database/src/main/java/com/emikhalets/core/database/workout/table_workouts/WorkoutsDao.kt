package com.emikhalets.core.database.workout.table_workouts

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutsDao : AppDao<WorkoutDb> {

    @Query("DELETE FROM workout")
    suspend fun drop()

    @Query("SELECT * FROM workout")
    suspend fun getAll(): List<WorkoutDb>

    @Query("SELECT * FROM workout")
    fun getAllFlow(): Flow<List<WorkoutDb>>

    @Query("SELECT * FROM workout WHERE id = :id")
    suspend fun getItem(id: Long): WorkoutDb

    @Query("SELECT * FROM workout WHERE id = :id")
    fun getItemFlow(id: Long): Flow<WorkoutDb>
}
package com.emikhalets.fitness.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutsDao {

    @Insert
    suspend fun insert(list: List<WorkoutDB>): List<Long>

    @Update
    suspend fun update(entity: WorkoutDB): Int

    @Query("SELECT * FROM workouts WHERE type = :type ORDER BY stage ASC")
    fun getAllFlow(type: String): Flow<List<WorkoutDB>>

    @Query("SELECT stage FROM workouts")
    suspend fun getAll(): List<Int>
}

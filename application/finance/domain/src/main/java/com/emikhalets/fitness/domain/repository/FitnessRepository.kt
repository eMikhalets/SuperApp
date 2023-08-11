package com.emikhalets.fitness.domain.repository

import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.WorkoutType
import kotlinx.coroutines.flow.Flow

interface FitnessRepository {

    suspend fun getWorkouts(type: WorkoutType): Result<Flow<List<WorkoutEntity>>>

    suspend fun getAllStages(): Result<List<Int>>

    suspend fun updateWorkout(entity: WorkoutEntity): Result<Int>

    suspend fun insertWorkouts(entities: List<WorkoutEntity>): Result<List<Long>>
}

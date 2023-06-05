package com.emikhalets.fitness.data.repository

import com.emikhalets.fitness.data.database.WorkoutsDao
import com.emikhalets.fitness.data.mapper.WorkoutMapper
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.WorkoutType
import com.emikhalets.fitness.domain.repository.FitnessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FitnessRepositoryImpl @Inject constructor(
    private val workoutsDao: WorkoutsDao,
) : FitnessRepository {

    override suspend fun getWorkouts(type: WorkoutType): Result<Flow<List<WorkoutEntity>>> {
        return runCatching {
            workoutsDao.getAllFlow(type.toString())
                .map { WorkoutMapper.mapDbListToList(it) }
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun getAllStages(): Result<List<Int>> {
        return runCatching { workoutsDao.getAll() }
            .onFailure { it.printStackTrace() }
    }

    override suspend fun updateWorkout(entity: WorkoutEntity): Result<Int> {
        return runCatching {
            val dbEntity = WorkoutMapper.mapEntityToDb(entity)
            workoutsDao.update(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun insertWorkouts(entities: List<WorkoutEntity>): Result<List<Long>> {
        return runCatching {
            val dbList = WorkoutMapper.mapListToDbList(entities)
            workoutsDao.insert(dbList)
        }.onFailure { it.printStackTrace() }
    }
}

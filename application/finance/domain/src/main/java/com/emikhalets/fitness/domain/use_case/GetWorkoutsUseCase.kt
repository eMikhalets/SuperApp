package com.emikhalets.fitness.domain.use_case

import com.emikhalets.fitness.domain.repository.FitnessRepository
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.WorkoutType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val repository: FitnessRepository,
) {

    suspend operator fun invoke(): Result<List<Int>> {
        return repository.getAllStages()
    }

    suspend operator fun invoke(type: WorkoutType): Result<Flow<List<WorkoutEntity>>> {
        return repository.getWorkouts(type)
    }
}

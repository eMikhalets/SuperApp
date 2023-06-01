package com.emikhalets.fitness.domain.use_case

import com.emikhalets.fitness.domain.repository.FitnessRepository
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import javax.inject.Inject

class UpdateWorkoutUseCase @Inject constructor(
    private val repository: FitnessRepository,
) {

    suspend operator fun invoke(entity: WorkoutEntity): Result<Int> {
        return repository.updateWorkout(entity)
    }
}

package com.emikhalets.fitness.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.repository.FitnessRepository
import javax.inject.Inject

class SaveProgramUseCase @Inject constructor(
    private val repository: FitnessRepository,
) {

    suspend operator fun invoke(name: String, workouts: List<WorkoutEntity>): AppResult<Unit> {
        val entity = ProgramEntity(name, workouts)
        return repository.insertProgram(entity)
    }
}

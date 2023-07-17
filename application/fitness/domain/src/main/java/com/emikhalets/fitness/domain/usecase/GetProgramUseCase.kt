package com.emikhalets.fitness.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.repository.FitnessRepository
import javax.inject.Inject

class GetProgramUseCase @Inject constructor(
    private val repository: FitnessRepository,
) {

    suspend operator fun invoke(id: Long): AppResult<ProgramEntity> {
        return repository.getProgram(id)
    }
}

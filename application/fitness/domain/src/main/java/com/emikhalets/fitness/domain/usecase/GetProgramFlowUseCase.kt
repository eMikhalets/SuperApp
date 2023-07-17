package com.emikhalets.fitness.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.repository.FitnessRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetProgramFlowUseCase @Inject constructor(
    private val repository: FitnessRepository,
) {

    operator fun invoke(id: Long): AppResult<Flow<ProgramEntity>> {
        return repository.getProgramFlow(id)
    }
}

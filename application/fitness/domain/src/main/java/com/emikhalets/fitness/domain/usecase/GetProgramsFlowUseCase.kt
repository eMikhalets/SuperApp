package com.emikhalets.fitness.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.repository.FitnessRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetProgramsFlowUseCase @Inject constructor(
    private val repository: FitnessRepository,
) {

    operator fun invoke(): AppResult<Flow<List<ProgramEntity>>> {
        return repository.getProgramsFlow()
    }
}

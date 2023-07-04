package com.emikhalets.fitness.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.repository.FitnessRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProgramsUseCaseImpl @Inject constructor(
    private val repository: FitnessRepository,
) : ProgramsUseCase {

    override fun getAll(): AppResult<Flow<List<ProgramEntity>>> {
        return AppResult.success(flowOf())
    }

    override fun getItem(id: Long): AppResult<Flow<ProgramEntity>> {
        return AppResult.success(flowOf())
    }
}

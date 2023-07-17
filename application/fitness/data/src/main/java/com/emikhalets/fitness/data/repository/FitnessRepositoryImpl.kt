package com.emikhalets.fitness.data.repository

import com.emikhalets.core.common.AppResult
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.entity.enums.ProgramType
import com.emikhalets.fitness.domain.repository.FitnessRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FitnessRepositoryImpl @Inject constructor(
) : FitnessRepository {

    override fun getProgramsFlow(): AppResult<Flow<List<ProgramEntity>>> {
        return AppResult.success(flowOf(emptyList()))
    }

    override suspend fun insertProgram(entity: ProgramEntity): AppResult<Unit> {
        return AppResult.success(Unit)
    }

    override suspend fun updateProgram(entity: ProgramEntity): AppResult<Unit> {
        return AppResult.success(Unit)
    }

    override suspend fun deleteProgram(entity: ProgramEntity): AppResult<Unit> {
        return AppResult.success(Unit)
    }

    override fun getProgramFlow(id: Long): AppResult<Flow<ProgramEntity>> {
        return AppResult.success(flowOf(ProgramEntity("", emptyList(), ProgramType.Dynamic)))
    }

    override suspend fun getProgram(id: Long): AppResult<ProgramEntity> {
        return AppResult.success(ProgramEntity("", emptyList(), ProgramType.Dynamic))
    }
}
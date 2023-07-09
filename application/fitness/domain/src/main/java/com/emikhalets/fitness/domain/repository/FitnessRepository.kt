package com.emikhalets.fitness.domain.repository

import com.emikhalets.core.common.AppResult
import com.emikhalets.fitness.domain.entity.ProgramEntity
import kotlinx.coroutines.flow.Flow

interface FitnessRepository {

    fun getProgramsFlow(): AppResult<Flow<List<ProgramEntity>>>

    suspend fun insertProgram(entity: ProgramEntity): AppResult<Unit>

    suspend fun updateProgram(entity: ProgramEntity): AppResult<Unit>

    suspend fun deleteProgram(entity: ProgramEntity): AppResult<Unit>

    fun getProgramFlow(id: Long): AppResult<Flow<ProgramEntity>>

    suspend fun getProgram(id: Long): AppResult<ProgramEntity>
}

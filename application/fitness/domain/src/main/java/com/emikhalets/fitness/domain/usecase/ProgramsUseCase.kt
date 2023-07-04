package com.emikhalets.fitness.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.fitness.domain.entity.ProgramEntity
import kotlinx.coroutines.flow.Flow

interface ProgramsUseCase {

    fun getAll(): AppResult<Flow<List<ProgramEntity>>>

    fun getItem(id: Long): AppResult<Flow<ProgramEntity>>

    fun delete(entity: ProgramEntity): AppResult<Unit>
}

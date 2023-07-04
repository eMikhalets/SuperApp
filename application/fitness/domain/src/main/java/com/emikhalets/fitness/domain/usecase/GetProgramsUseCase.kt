package com.emikhalets.fitness.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.fitness.domain.entity.ProgramEntity
import kotlinx.coroutines.flow.Flow

interface GetProgramsUseCase {

    suspend operator fun invoke(): AppResult<Flow<List<ProgramEntity>>>
}

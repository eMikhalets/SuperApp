package com.emikhalets.notes.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.notes.domain.entity.SubtaskEntity

interface SubtasksUseCase {

    suspend fun insert(entity: SubtaskEntity): AppResult<Unit>

    suspend fun update(entity: SubtaskEntity): AppResult<Unit>

    suspend fun delete(entity: SubtaskEntity): AppResult<Unit>
}
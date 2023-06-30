package com.emikhalets.notes.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.notes.domain.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TasksUseCase {

    suspend fun insert(entity: TaskEntity): AppResult<Unit>

    suspend fun update(entity: TaskEntity): AppResult<Unit>

    suspend fun delete(entity: TaskEntity): AppResult<Unit>

    fun getAllFlow(): AppResult<Flow<List<TaskEntity>>>
}
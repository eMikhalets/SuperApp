package com.emikhalets.superapp.feature.notes.domain

import com.emikhalets.superapp.core.common.AppResult
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getTasks(): Flow<List<TaskModel>>

    suspend fun insertTask(model: TaskModel): AppResult<Boolean>

    suspend fun updateTask(model: TaskModel): AppResult<Boolean>

    suspend fun deleteTask(model: TaskModel): AppResult<Boolean>

    suspend fun insertSubTask(model: SubTaskModel): AppResult<Boolean>

    suspend fun updateSubTask(model: SubTaskModel): AppResult<Boolean>

    suspend fun deleteSubTask(model: SubTaskModel): AppResult<Boolean>
}
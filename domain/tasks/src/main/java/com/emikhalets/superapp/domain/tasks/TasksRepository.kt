package com.emikhalets.superapp.domain.tasks

import com.emikhalets.superapp.core.common.AppResult
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getTasks(): Flow<List<TaskModel>>

    suspend fun insertTask(model: TaskModel): AppResult<Long>

    suspend fun updateTask(model: TaskModel): AppResult<Int>

    suspend fun deleteTask(model: TaskModel): AppResult<Int>
}
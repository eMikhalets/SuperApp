package com.emikhalets.superapp.domain.notes.repository

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.domain.notes.model.SubtaskModel
import com.emikhalets.superapp.domain.notes.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getTasks(): Flow<List<TaskModel>>

    suspend fun getTask(id: Long): AppResult<TaskModel>

    suspend fun insertTask(model: TaskModel): AppResult<Long>

    suspend fun updateTask(model: TaskModel): AppResult<Int>

    suspend fun deleteTask(model: TaskModel): AppResult<Int>

    suspend fun updateSubtask(model: SubtaskModel): AppResult<Int>

    suspend fun deleteSubtask(model: SubtaskModel): AppResult<Int>
}
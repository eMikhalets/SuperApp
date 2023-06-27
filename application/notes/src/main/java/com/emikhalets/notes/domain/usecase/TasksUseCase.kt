package com.emikhalets.notes.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TasksUseCase @Inject constructor(
    private val appRepo: NotesRepository,
) {

    suspend fun insert(entity: TaskEntity): AppResult<Unit> {
        return appRepo.insertTask(entity)
    }

    suspend fun update(entity: TaskEntity): AppResult<Unit> {
        return appRepo.updateTask(entity)
    }

    suspend fun delete(entity: TaskEntity): AppResult<Unit> {
        return appRepo.deleteTask(entity)
    }

    fun getAllFlow(): AppResult<Flow<List<TaskEntity>>> {
        return appRepo.getTasks()
    }
}
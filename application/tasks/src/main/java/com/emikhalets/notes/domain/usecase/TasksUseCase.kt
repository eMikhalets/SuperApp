package com.emikhalets.notes.domain.usecase

import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksUseCase @Inject constructor(
    private val appRepo: NotesRepository,
) {

    suspend fun insert(entity: TaskEntity): Result<Unit> {
        return appRepo.insertTask(entity)
    }

    suspend fun update(entity: TaskEntity): Result<Unit> {
        return appRepo.updateTask(entity)
    }

    suspend fun delete(entity: TaskEntity): Result<Unit> {
        return appRepo.deleteTask(entity)
    }

    suspend fun getAllFlow(): Result<Flow<List<TaskEntity>>> {
        return appRepo.getTasks()
    }
}
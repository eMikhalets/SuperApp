package com.emikhalets.notes.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TasksUseCaseImpl @Inject constructor(
    private val appRepo: NotesRepository,
) : TasksUseCase {

    override suspend fun insert(entity: TaskEntity): AppResult<Unit> {
        logi(TAG, "Insert: entity = $entity")
        return appRepo.insertTask(entity)
    }

    override suspend fun update(entity: TaskEntity): AppResult<Unit> {
        logi(TAG, "Update: entity = $entity")
        return appRepo.updateTask(entity)
    }

    override suspend fun delete(entity: TaskEntity): AppResult<Unit> {
        logi(TAG, "Delete: entity = $entity")
        return appRepo.deleteTask(entity)
    }

    override fun getAllFlow(): AppResult<Flow<List<TaskEntity>>> {
        logi(TAG, "Get all tasks")
        return appRepo.getTasks()
    }

    companion object {

        private const val TAG = "TasksUseCase"
    }
}
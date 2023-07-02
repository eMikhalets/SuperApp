package com.emikhalets.notes.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject

class SubtasksUseCaseImpl @Inject constructor(
    private val appRepo: NotesRepository,
) : SubtasksUseCase {

    override suspend fun insert(entity: SubtaskEntity): AppResult<Unit> {
        logi(TAG, "insert(): entity = $entity")
        return appRepo.insertSubtask(entity)
    }

    override suspend fun insert(entities: List<SubtaskEntity>): AppResult<Unit> {
        logi(TAG, "insert(): size = ${entities.count()}")
        return appRepo.insertSubtasks(entities)
    }

    override suspend fun update(entity: SubtaskEntity): AppResult<Unit> {
        logi(TAG, "update(): entity = $entity")
        return appRepo.updateSubtask(entity)
    }

    override suspend fun delete(entity: SubtaskEntity): AppResult<Unit> {
        logi(TAG, "delete(): entity = $entity")
        return appRepo.deleteSubtask(entity)
    }

    companion object {

        private const val TAG = "SubtasksUseCase"
    }
}
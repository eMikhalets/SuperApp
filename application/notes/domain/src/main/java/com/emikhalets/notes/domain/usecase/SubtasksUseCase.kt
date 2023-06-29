package com.emikhalets.notes.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject

class SubtasksUseCase @Inject constructor(
    private val appRepo: NotesRepository,
) {

    suspend fun insert(entity: SubtaskEntity): AppResult<Unit> {
        return appRepo.insertSubtask(entity)
    }

    suspend fun update(entity: SubtaskEntity): AppResult<Unit> {
        return appRepo.updateSubtask(entity)
    }

    suspend fun delete(entity: SubtaskEntity): AppResult<Unit> {
        return appRepo.deleteSubtask(entity)
    }
}
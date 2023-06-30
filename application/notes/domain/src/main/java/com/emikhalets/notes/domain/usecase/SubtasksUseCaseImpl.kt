package com.emikhalets.notes.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject

class SubtasksUseCaseImpl @Inject constructor(
    private val appRepo: NotesRepository,
) : SubtasksUseCase {

    override suspend fun insert(entity: SubtaskEntity): AppResult<Unit> {
        return appRepo.insertSubtask(entity)
    }

    override suspend fun update(entity: SubtaskEntity): AppResult<Unit> {
        return appRepo.updateSubtask(entity)
    }

    override suspend fun delete(entity: SubtaskEntity): AppResult<Unit> {
        return appRepo.deleteSubtask(entity)
    }
}
package com.emikhalets.notes.domain.usecase.notes

import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UpdateNoteUseCase @Inject constructor(
    private val appRepo: NotesRepository,
) {

    suspend operator fun invoke(entity: NoteEntity): AppResult<Unit> {
        logi("UpdateNoteUC", "Update: entity = $entity")
        return appRepo.updateNote(entity)
    }
}
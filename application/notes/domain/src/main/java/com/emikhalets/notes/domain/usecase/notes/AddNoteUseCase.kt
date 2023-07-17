package com.emikhalets.notes.domain.usecase.notes

import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val appRepo: NotesRepository,
) {

    suspend operator fun invoke(entity: NoteEntity): AppResult<Unit> {
        logi("AddNoteUC", "Insert: entity = $entity")
        return appRepo.insertNote(entity)
    }
}
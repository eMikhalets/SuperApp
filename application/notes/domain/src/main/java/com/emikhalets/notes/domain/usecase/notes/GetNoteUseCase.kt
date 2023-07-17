package com.emikhalets.notes.domain.usecase.notes

import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val appRepo: NotesRepository,
) {

    suspend operator fun invoke(id: Long): AppResult<NoteEntity> {
        logi("GetNoteUseCase", "Get note: id = $id")
        return appRepo.getNote(id)
    }
}
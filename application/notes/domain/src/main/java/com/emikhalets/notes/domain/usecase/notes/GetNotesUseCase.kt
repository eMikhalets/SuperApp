package com.emikhalets.notes.domain.usecase.notes

import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase @Inject constructor(
    private val appRepo: NotesRepository,
) {

    operator fun invoke(): AppResult<Flow<List<NoteEntity>>> {
        logi("GetNotesUC", "Get all notes")
        return appRepo.getNotes()
    }
}
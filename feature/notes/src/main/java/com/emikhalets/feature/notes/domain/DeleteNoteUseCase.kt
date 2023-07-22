package com.emikhalets.feature.notes.domain

import com.emikhalets.feature.notes.data.Repository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(model: NoteModel) {
        repository.deleteNote(model)
    }
}
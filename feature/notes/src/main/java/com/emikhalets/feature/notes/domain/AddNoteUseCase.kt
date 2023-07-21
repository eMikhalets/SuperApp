package com.emikhalets.feature.notes.domain

import com.emikhalets.feature.notes.data.Repository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(model: NoteModel) {
        if (model.id == 0L) repository.insertNote(model)
        else repository.updateNote(model)
    }
}
package com.emikhalets.simplenotes.domain.use_cases.notes

import com.emikhalets.simplenotes.domain.AppRepository
import com.emikhalets.simplenotes.domain.entities.NoteEntity
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke(entity: NoteEntity) = repository.updateNote(entity)
}
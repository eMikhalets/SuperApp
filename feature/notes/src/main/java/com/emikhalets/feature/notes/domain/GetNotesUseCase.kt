package com.emikhalets.feature.notes.domain

import com.emikhalets.feature.notes.data.Repository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<List<NoteModel>> {
        return repository.getNotes()
    }
}
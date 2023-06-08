package com.emikhalets.notes.domain.usecase

import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesUseCase @Inject constructor(
    private val appRepo: NotesRepository,
) {

    suspend fun insert(entity: NoteEntity): Result<Unit> {
        return appRepo.insertNote(entity)
    }

    suspend fun update(entity: NoteEntity): Result<Unit> {
        return appRepo.updateNote(entity)
    }

    suspend fun delete(entity: NoteEntity): Result<Unit> {
        return appRepo.deleteNote(entity)
    }

    suspend fun getAllFlow(): Result<Flow<List<NoteEntity>>> {
        return appRepo.getNotes()
    }
}
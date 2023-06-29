package com.emikhalets.notes.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class NotesUseCase @Inject constructor(
    private val appRepo: NotesRepository,
) {

    suspend fun insert(entity: NoteEntity): AppResult<Unit> {
        return appRepo.insertNote(entity)
    }

    suspend fun update(entity: NoteEntity): AppResult<Unit> {
        return appRepo.updateNote(entity)
    }

    suspend fun delete(entity: NoteEntity): AppResult<Unit> {
        return appRepo.deleteNote(entity)
    }

    fun getAllFlow(): AppResult<Flow<List<NoteEntity>>> {
        return appRepo.getNotes()
    }

    suspend fun getItem(id: Long): AppResult<NoteEntity> {
        return appRepo.getNote(id)
    }
}
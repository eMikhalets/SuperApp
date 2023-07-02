package com.emikhalets.notes.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class NotesUseCaseImpl @Inject constructor(
    private val appRepo: NotesRepository,
) : NotesUseCase {

    override suspend fun insert(entity: NoteEntity): AppResult<Unit> {
        logi(TAG, "insert(): entity = $entity")
        return appRepo.insertNote(entity)
    }

    override suspend fun update(entity: NoteEntity): AppResult<Unit> {
        logi(TAG, "update(): entity = $entity")
        return appRepo.updateNote(entity)
    }

    override suspend fun delete(entity: NoteEntity): AppResult<Unit> {
        logi(TAG, "delete(): entity = $entity")
        return appRepo.deleteNote(entity)
    }

    override fun getAllFlow(): AppResult<Flow<List<NoteEntity>>> {
        logi(TAG, "getAllFlow()")
        return appRepo.getNotes()
    }

    override suspend fun getItem(id: Long): AppResult<NoteEntity> {
        logi(TAG, "getItem(): id = $id")
        return appRepo.getNote(id)
    }

    companion object {

        private const val TAG = "NotesUseCase"
    }
}
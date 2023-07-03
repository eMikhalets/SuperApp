package com.emikhalets.notes.domain.usecase

import com.emikhalets.core.common.AppResult
import com.emikhalets.notes.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesUseCase {

    suspend fun insert(entity: NoteEntity): AppResult<Unit>

    suspend fun update(entity: NoteEntity): AppResult<Unit>

    suspend fun delete(entity: NoteEntity): AppResult<Unit>

    fun getAllFlow(): AppResult<Flow<List<NoteEntity>>>

    suspend fun getItem(id: Long): AppResult<NoteEntity>
}
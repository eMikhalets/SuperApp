package com.emikhalets.notes.domain.repository

import com.emikhalets.core.common.AppResult
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun insertNote(entity: NoteEntity): AppResult<Unit>

    suspend fun updateNote(entity: NoteEntity): AppResult<Unit>

    suspend fun deleteNote(entity: NoteEntity): AppResult<Unit>

    fun getNotes(): AppResult<Flow<List<NoteEntity>>>

    suspend fun getNote(id: Long): AppResult<NoteEntity>

    suspend fun insertTask(entity: TaskEntity): AppResult<Unit>

    suspend fun updateTask(entity: TaskEntity): AppResult<Unit>

    suspend fun deleteTask(entity: TaskEntity): AppResult<Unit>

    fun getTasks(): AppResult<Flow<List<TaskEntity>>>
}
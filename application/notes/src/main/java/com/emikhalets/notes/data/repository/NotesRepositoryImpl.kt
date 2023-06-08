package com.emikhalets.notes.data.repository

import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.execute
import com.emikhalets.notes.data.database.table_notes.NotesDao
import com.emikhalets.notes.data.mappers.NotesMapper
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao,
) : NotesRepository {

    override suspend fun insertNote(entity: NoteEntity): AppResult<Unit> {
        return execute {
            notesDao.insert(NotesMapper.mapEntityToDb(entity))
        }
    }

    override suspend fun updateNote(entity: NoteEntity): AppResult<Unit> {
        return execute {
            notesDao.update(NotesMapper.mapEntityToDb(entity))
        }
    }

    override suspend fun deleteNote(entity: NoteEntity): AppResult<Unit> {
        return execute {
            notesDao.delete(NotesMapper.mapEntityToDb(entity))
        }
    }

    override fun getNotes(): AppResult<Flow<List<NoteEntity>>> {
        return execute {
            notesDao.getAllFlow().map {
                NotesMapper.mapDbListToEntityList(it)
            }
        }
    }
}

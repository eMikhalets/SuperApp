package com.emikhalets.notes.data.repository

import com.emikhalets.common.AppResult
import com.emikhalets.common.execute
import com.emikhalets.notes.data.database.table_notes.NotesDao
import com.emikhalets.notes.data.database.table_tasks.TasksDao
import com.emikhalets.notes.data.mappers.NotesMapper
import com.emikhalets.notes.data.mappers.TasksMapper
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
    private val notesDao: NotesDao,
) : NotesRepository {

    override suspend fun insertTask(entity: TaskEntity): AppResult<Unit> {
        return execute {
            tasksDao.insert(TasksMapper.mapEntityToDb(entity))
        }
    }

    override suspend fun updateTask(entity: TaskEntity): AppResult<Unit> {
        return execute {
            tasksDao.update(TasksMapper.mapEntityToDb(entity))
        }
    }

    override suspend fun deleteTask(entity: TaskEntity): AppResult<Unit> {
        return execute {
            tasksDao.delete(TasksMapper.mapEntityToDb(entity))
        }
    }

    override fun getTasks(): AppResult<Flow<List<TaskEntity>>> {
        return execute {
            tasksDao.getAllFlow().map { list ->
                val mapped = TasksMapper.mapDbListToEntityList(list)
                mapped.sortedBy { it.isCompleted }
            }
        }
    }

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

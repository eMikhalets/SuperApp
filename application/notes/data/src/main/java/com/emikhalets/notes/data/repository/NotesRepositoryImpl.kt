package com.emikhalets.notes.data.repository

import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.execute
import com.emikhalets.notes.data.database.table_notes.NotesDao
import com.emikhalets.notes.data.database.table_subtasks.SubtasksDao
import com.emikhalets.notes.data.database.table_tasks.TasksDao
import com.emikhalets.notes.data.mappers.NotesMapper
import com.emikhalets.notes.data.mappers.SubtasksMapper
import com.emikhalets.notes.data.mappers.TasksMapper
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.domain.repository.NotesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao,
    private val tasksDao: TasksDao,
    private val subtasksDao: SubtasksDao,
) : NotesRepository {

    override suspend fun insertNote(entity: NoteEntity): AppResult<Unit> {
        return execute { notesDao.insert(NotesMapper.mapEntityToDb(entity)) }
    }

    override suspend fun updateNote(entity: NoteEntity): AppResult<Unit> {
        return execute { notesDao.update(NotesMapper.mapEntityToDb(entity)) }
    }

    override suspend fun deleteNote(entity: NoteEntity): AppResult<Unit> {
        return execute { notesDao.delete(NotesMapper.mapEntityToDb(entity)) }
    }

    override fun getNotes(): AppResult<Flow<List<NoteEntity>>> {
        return execute { notesDao.getAllFlow().map { NotesMapper.mapDbListToEntityList(it) } }
    }

    override suspend fun getNote(id: Long): AppResult<NoteEntity> {
        return execute { NotesMapper.mapDbToEntity(notesDao.getItemById(id)) }
    }

    override suspend fun insertTask(entity: TaskEntity): AppResult<Unit> {
        return execute {
            val id = tasksDao.insert(TasksMapper.mapEntityToDb(entity))
            entity.subtasks.forEach {
                subtasksDao.insert(SubtasksMapper.mapEntityToDb(it.copy(taskId = id)))
            }
        }
    }

    override suspend fun updateTask(entity: TaskEntity): AppResult<Unit> {
        return execute { tasksDao.update(TasksMapper.mapEntityToDb(entity)) }
    }

    override suspend fun deleteTask(entity: TaskEntity): AppResult<Unit> {
        return execute { tasksDao.delete(TasksMapper.mapEntityToDb(entity)) }
    }

    override fun getTasks(): AppResult<Flow<List<TaskEntity>>> {
        return execute { tasksDao.getAllFlow().map { TasksMapper.mapDbListToEntityList(it) } }
    }

    override suspend fun insertSubtask(entity: SubtaskEntity): AppResult<Unit> {
        return execute { subtasksDao.insert(SubtasksMapper.mapEntityToDb(entity)) }
    }

    override suspend fun insertSubtasks(entities: List<SubtaskEntity>): AppResult<Unit> {
        return execute { subtasksDao.insert(SubtasksMapper.mapEntityListToDbList(entities)) }
    }

    override suspend fun updateSubtask(entity: SubtaskEntity): AppResult<Unit> {
        return execute { subtasksDao.update(SubtasksMapper.mapEntityToDb(entity)) }
    }

    override suspend fun deleteSubtask(entity: SubtaskEntity): AppResult<Unit> {
        return execute { subtasksDao.delete(SubtasksMapper.mapEntityToDb(entity)) }
    }
}

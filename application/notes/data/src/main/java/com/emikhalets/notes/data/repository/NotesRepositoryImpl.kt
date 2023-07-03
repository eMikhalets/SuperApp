package com.emikhalets.notes.data.repository

import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.execute
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.logi
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
        logi(TAG, "Insert note: entity = $entity")
        return execute { notesDao.insert(NotesMapper.mapEntityToDb(entity)) }
    }

    override suspend fun updateNote(entity: NoteEntity): AppResult<Unit> {
        logi(TAG, "Update note: entity = $entity")
        return execute { notesDao.update(NotesMapper.mapEntityToDb(entity)) }
    }

    override suspend fun deleteNote(entity: NoteEntity): AppResult<Unit> {
        logi(TAG, "Delete note: entity = $entity")
        return execute { notesDao.delete(NotesMapper.mapEntityToDb(entity)) }
    }

    override fun getNotes(): AppResult<Flow<List<NoteEntity>>> {
        logi(TAG, "Get all notes")
        return execute { notesDao.getAllFlow().map { NotesMapper.mapDbListToEntityList(it) } }
    }

    override suspend fun getNote(id: Long): AppResult<NoteEntity> {
        logi(TAG, "Get note: id = $id")
        return execute { NotesMapper.mapDbToEntity(notesDao.getItemById(id)) }
    }

    override suspend fun insertTask(entity: TaskEntity): AppResult<Unit> {
        logi(TAG, "Insert task: entity = $entity")
        return execute {
            logd(TAG, "Inserting task = $entity")
            val id = tasksDao.insert(TasksMapper.mapEntityToDb(entity))
            entity.subtasks.forEach {
                val subtask = it.copy(taskId = id)
                logd(TAG, "Inserting subtask = $subtask")
                subtasksDao.insert(SubtasksMapper.mapEntityToDb(subtask))
            }
        }
    }

    override suspend fun updateTask(entity: TaskEntity): AppResult<Unit> {
        logi(TAG, "Update task: entity = $entity")
        return execute {
            entity.subtasks.forEach {
                if (it.id == 0L) {
                    val subtask = it.copy(taskId = entity.id)
                    logd(TAG, "Inserting subtask = $subtask")
                    subtasksDao.insert(SubtasksMapper.mapEntityToDb(subtask))
                } else {
                    logd(TAG, "Updating subtask = $it")
                    subtasksDao.update(SubtasksMapper.mapEntityToDb(it))
                }
            }
            logd(TAG, "Updating task = $entity")
            tasksDao.update(TasksMapper.mapEntityToDb(entity))
        }
    }

    override suspend fun deleteTask(entity: TaskEntity): AppResult<Unit> {
        logi(TAG, "Delete task: entity = $entity")
        return execute { tasksDao.delete(TasksMapper.mapEntityToDb(entity)) }
    }

    override fun getTasks(): AppResult<Flow<List<TaskEntity>>> {
        logi(TAG, "Get all tasks")
        return execute { tasksDao.getAllFlow().map { TasksMapper.mapDbListToEntityList(it) } }
    }

    override suspend fun insertSubtask(entity: SubtaskEntity): AppResult<Unit> {
        logi(TAG, "Insert subtask: entity = $entity")
        return execute { subtasksDao.insert(SubtasksMapper.mapEntityToDb(entity)) }
    }

    override suspend fun insertSubtasks(entities: List<SubtaskEntity>): AppResult<Unit> {
        logi(TAG, "Insert subtasks: size = ${entities.count()}")
        return execute { subtasksDao.insert(SubtasksMapper.mapEntityListToDbList(entities)) }
    }

    override suspend fun updateSubtask(entity: SubtaskEntity): AppResult<Unit> {
        logi(TAG, "Update subtask: entity = $entity")
        return execute { subtasksDao.update(SubtasksMapper.mapEntityToDb(entity)) }
    }

    override suspend fun deleteSubtask(entity: SubtaskEntity): AppResult<Unit> {
        logi(TAG, "Delete subtask: entity = $entity")
        return execute { subtasksDao.delete(SubtasksMapper.mapEntityToDb(entity)) }
    }

    companion object {

        private const val TAG = "NotesRepository"
    }
}

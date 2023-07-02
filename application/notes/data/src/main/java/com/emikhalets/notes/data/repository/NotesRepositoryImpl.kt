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
        logi(TAG, "insertNote(): entity = $entity")
        return execute { notesDao.insert(NotesMapper.mapEntityToDb(entity)) }
    }

    override suspend fun updateNote(entity: NoteEntity): AppResult<Unit> {
        logi(TAG, "updateNote(): entity = $entity")
        return execute { notesDao.update(NotesMapper.mapEntityToDb(entity)) }
    }

    override suspend fun deleteNote(entity: NoteEntity): AppResult<Unit> {
        logi(TAG, "deleteNote(): entity = $entity")
        return execute { notesDao.delete(NotesMapper.mapEntityToDb(entity)) }
    }

    override fun getNotes(): AppResult<Flow<List<NoteEntity>>> {
        logi(TAG, "getNotes()")
        return execute { notesDao.getAllFlow().map { NotesMapper.mapDbListToEntityList(it) } }
    }

    override suspend fun getNote(id: Long): AppResult<NoteEntity> {
        logi(TAG, "getNote(): id = $id")
        return execute { NotesMapper.mapDbToEntity(notesDao.getItemById(id)) }
    }

    override suspend fun insertTask(entity: TaskEntity): AppResult<Unit> {
        logi(TAG, "insertTask(): entity = $entity")
        return execute {
            val id = tasksDao.insert(TasksMapper.mapEntityToDb(entity))
            entity.subtasks.forEach {
                subtasksDao.insert(SubtasksMapper.mapEntityToDb(it.copy(taskId = id)))
            }
        }
    }

    override suspend fun updateTask(entity: TaskEntity): AppResult<Unit> {
        logi(TAG, "updateTask(): entity = $entity")
        return execute {
            entity.subtasks.forEach {
                if (it.id == 0L) {
                    logd(TAG, "Inserting subtask = ${it.copy(taskId = entity.id)}")
                    subtasksDao.insert(SubtasksMapper.mapEntityToDb(it))
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
        logi(TAG, "deleteTask(): entity = $entity")
        return execute { tasksDao.delete(TasksMapper.mapEntityToDb(entity)) }
    }

    override fun getTasks(): AppResult<Flow<List<TaskEntity>>> {
        logi(TAG, "getTasks()")
        return execute { tasksDao.getAllFlow().map { TasksMapper.mapDbListToEntityList(it) } }
    }

    override suspend fun insertSubtask(entity: SubtaskEntity): AppResult<Unit> {
        logi(TAG, "insertSubtask(): entity = $entity")
        return execute { subtasksDao.insert(SubtasksMapper.mapEntityToDb(entity)) }
    }

    override suspend fun insertSubtasks(entities: List<SubtaskEntity>): AppResult<Unit> {
        logi(TAG, "insertSubtasks(): size = ${entities.count()}")
        return execute { subtasksDao.insert(SubtasksMapper.mapEntityListToDbList(entities)) }
    }

    override suspend fun updateSubtask(entity: SubtaskEntity): AppResult<Unit> {
        logi(TAG, "updateSubtask(): entity = $entity")
        return execute { subtasksDao.update(SubtasksMapper.mapEntityToDb(entity)) }
    }

    override suspend fun deleteSubtask(entity: SubtaskEntity): AppResult<Unit> {
        logi(TAG, "deleteSubtask(): entity = $entity")
        return execute { subtasksDao.delete(SubtasksMapper.mapEntityToDb(entity)) }
    }

    companion object {

        private const val TAG = "NotesRepository"
    }
}

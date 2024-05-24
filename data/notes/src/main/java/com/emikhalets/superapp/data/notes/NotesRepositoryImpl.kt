package com.emikhalets.superapp.data.notes

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.invoke
import com.emikhalets.superapp.data.notes.table_tasks.TasksDao
import com.emikhalets.superapp.domain.notes.model.SubtaskModel
import com.emikhalets.superapp.domain.notes.model.TaskModel
import com.emikhalets.superapp.domain.notes.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
) : NotesRepository {

    override fun getTasks(): Flow<List<TaskModel>> {
        Timber.d("getTasks()")
        val result = tasksDao.getAllFlow()
        return TasksMapper.toModelFlow(result)
    }

    override suspend fun getTask(id: Long): AppResult<TaskModel> {
        Timber.d("getTask($id)")
        return invoke {
            val result = tasksDao.getItem(id)
            TasksMapper.toModel(result)
        }
    }

    override suspend fun insertTask(model: TaskModel): AppResult<Long> {
        Timber.d("insertTask($model)")
        return invoke {
            val mappedModel = TasksMapper.toDb(model)
            val id = tasksDao.insert(mappedModel)
            val mappedList = TasksMapper.toDbList(model.subtasks, id)
            tasksDao.insert(mappedList)
            id
        }
    }

    override suspend fun updateTask(model: TaskModel): AppResult<Int> {
        Timber.d("updateTask($model)")
        return invoke {
            model.subtasks.forEach {
                if (it.id == 0L) {
                    val mappedModel = TasksMapper.toDb(it, model.id)
                    tasksDao.insert(mappedModel)
                } else {
                    val mappedModel = TasksMapper.toDb(model)
                    tasksDao.update(mappedModel)
                }
            }
            tasksDao.update(TasksMapper.toDb(model))
        }
    }

    override suspend fun deleteTask(model: TaskModel): AppResult<Int> {
        Timber.d("deleteTask($model)")
        return invoke {
            val mappedModel = TasksMapper.toDb(model)
            tasksDao.deleteByParentId(model.id)
            tasksDao.delete(mappedModel)
        }
    }

    override suspend fun updateSubtask(model: SubtaskModel): AppResult<Int> {
        Timber.d("updateSubtask($model)")
        return invoke {
            val mappedModel = TasksMapper.toDb(model)
            tasksDao.update(mappedModel)
        }
    }

    override suspend fun deleteSubtask(model: SubtaskModel): AppResult<Int> {
        Timber.d("deleteSubtask($model)")
        return invoke {
            val mappedModel = TasksMapper.toDb(model)
            tasksDao.delete(mappedModel)
        }
    }
}
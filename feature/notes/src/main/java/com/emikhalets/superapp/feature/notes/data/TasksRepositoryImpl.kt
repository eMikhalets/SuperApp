package com.emikhalets.superapp.feature.notes.data

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.invoke
import com.emikhalets.superapp.core.database.notes.table_subtasks.SubTasksDao
import com.emikhalets.superapp.core.database.notes.table_tasks.TasksDao
import com.emikhalets.superapp.feature.notes.domain.SubTaskModel
import com.emikhalets.superapp.feature.notes.domain.TaskModel
import com.emikhalets.superapp.feature.notes.domain.TasksRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
    private val subTasksDao: SubTasksDao,
) : TasksRepository {

    override fun getTasks(): Flow<List<TaskModel>> {
        Timber.d("getTasks)")
        return tasksDao.getAllFlow().toModel()
    }

    override suspend fun insertTask(model: TaskModel): AppResult<Boolean> {
        Timber.d("insertTask with content ${model.header}")
        return invoke {
            val taskId = tasksDao.insert(model.toDb())
            taskId > 0
        }
    }

    override suspend fun updateTask(model: TaskModel): AppResult<Boolean> {
        Timber.d("updateTask with id ${model.id}")
        return invoke {
            val count = tasksDao.update(model.toDb())
            count > 0
        }
    }

    override suspend fun deleteTask(model: TaskModel): AppResult<Boolean> {
        Timber.d("deleteTask with id ${model.id}")
        return invoke {
            subTasksDao.deleteByParentId(model.id)
            val count = tasksDao.delete(model.toDb())
            count > 0
        }
    }

    override suspend fun insertSubTask(model: SubTaskModel): AppResult<Boolean> {
        Timber.d("insertSubTask with content ${model.text}")
        return invoke {
            val taskId = subTasksDao.insert(model.toDb())
            taskId > 0
        }
    }

    override suspend fun updateSubTask(model: SubTaskModel): AppResult<Boolean> {
        Timber.d("updateSubTask with id ${model.id}")
        return invoke {
            val count = subTasksDao.update(model.toDb())
            count > 0
        }
    }

    override suspend fun deleteSubTask(model: SubTaskModel): AppResult<Boolean> {
        Timber.d("deleteSubTask with id ${model.id}")
        return invoke {
            val count = subTasksDao.delete(model.toDb())
            count > 0
        }
    }
}
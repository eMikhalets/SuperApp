package com.emikhalets.superapp.feature.notes.data

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.constant.Const
import com.emikhalets.superapp.core.common.invoke
import com.emikhalets.superapp.core.database.notes.table_tasks.TasksDao
import com.emikhalets.superapp.feature.notes.domain.TaskModel
import com.emikhalets.superapp.feature.notes.domain.TasksRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
) : TasksRepository {

    override fun getTasks(): Flow<List<TaskModel>> {
        Timber.d("getTasks()")
        val result = tasksDao.getAllFlow()
        return TasksMapper.toModelFlow(result)
    }

    override suspend fun insertTask(model: TaskModel): AppResult<Long> {
        Timber.d("insertTask($model)")
        return invoke {
            val taskDb = TasksMapper.toDb(model)
            val taskId = tasksDao.insert(taskDb)
            if (model.subtasks.isNotEmpty()) {
                val subtasksListDb = TasksMapper.toDbList(emptyList()/*model.subtasks*/, taskId)
                tasksDao.insert(subtasksListDb)
            }
            taskId
        }
    }

    override suspend fun updateTask(model: TaskModel): AppResult<Int> {
        Timber.d("updateTask($model)")
        return invoke {
            val updatedTasks = tasksDao.update(TasksMapper.toDb(model))
            model.subtasks.forEach {
                if (it.id == Const.IdNew) {
                    val mappedModel = TasksMapper.toDb(TaskModel()/*it*/, model.id)
                    tasksDao.insert(mappedModel)
                } else {
                    val mappedModel = TasksMapper.toDb(model)
                    tasksDao.update(mappedModel)
                }
            }
            updatedTasks
        }
    }

    override suspend fun deleteTask(model: TaskModel): AppResult<Int> {
        Timber.d("deleteTask($model)")
        return invoke {
            val mappedModel = TasksMapper.toDb(model)
            if (model.subtasks.isNotEmpty()) {
                tasksDao.deleteByParentId(model.id)
            }
            tasksDao.delete(mappedModel)
        }
    }
}
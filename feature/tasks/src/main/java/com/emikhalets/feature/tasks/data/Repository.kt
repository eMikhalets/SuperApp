package com.emikhalets.feature.tasks.data

import com.emikhalets.core.database.notes.table_tasks.TasksLocalDataSource
import com.emikhalets.feature.tasks.domain.TaskModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository @Inject constructor(
    private val localDataSource: TasksLocalDataSource,
) {

    suspend fun insertTask(model: TaskModel) {
        val id = localDataSource.insertTask(model.toDb())
        if (model.subtasks.isNotEmpty()) {
            localDataSource.insertTasks(model.subtasks.toDbList(id))
        }
    }

    suspend fun updateTask(model: TaskModel) {
        localDataSource.updateTask(model.toDb())
        model.subtasks.forEach {
            if (it.id == 0L) {
                localDataSource.insertTask(it.toDb(model.id))
            } else {
                localDataSource.updateTask(it.toDb(model.id))
            }
        }
    }

    suspend fun deleteTask(model: TaskModel) {
        localDataSource.deleteTask(model.toDb())
    }

    fun getTasks(): Flow<List<TaskModel>> {
        return localDataSource.getTasks().map { it.toModelList() }
    }
}
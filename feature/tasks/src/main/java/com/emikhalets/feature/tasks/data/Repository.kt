package com.emikhalets.feature.tasks.data

import com.emikhalets.core.common.logd
import com.emikhalets.core.common.logi
import com.emikhalets.core.database.notes.table_tasks.TasksLocalDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository @Inject constructor(
    private val localDataSource: TasksLocalDataSource,
) {

    suspend fun insertTask(model: TaskModel) {
        logi(TAG, "Insert task: model = $model")
        localDataSource.insertTask(model.toDb())
        if (model.subtasks.isNotEmpty()) {
            localDataSource.insertTasks(model.subtasks.toDbList(model.id))
        }
    }

    suspend fun updateTask(model: TaskModel) {
        logi(TAG, "Update task: model = $model")
        localDataSource.updateTask(model.toDb())
        model.subtasks.forEach {
            if (it.id == 0L) {
                logd(TAG, "Insert subtask: model = $it")
                localDataSource.insertTask(it.toDb(model.id))
            } else {
                logd(TAG, "Update subtask: model = $it")
                localDataSource.updateTask(it.toDb(model.id))
            }
        }
    }

    suspend fun deleteTask(model: TaskModel) {
        logi(TAG, "Delete task: model = $model")
        localDataSource.deleteTask(model.toDb())
    }

    fun getTasks(): Flow<List<TaskModel>> {
        logi(TAG, "Get all tasks")
        return localDataSource.getTasks().map { it.toModelList() }
    }

    companion object {

        private const val TAG = "AppNotesRepo"
    }
}
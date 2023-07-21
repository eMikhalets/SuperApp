package com.emikhalets.core.database.notes.table_tasks

import com.emikhalets.core.common.logi
import com.emikhalets.core.database.notes.embeded.TaskFullDb
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TasksLocalDataSource @Inject constructor(
    private val dao: TasksDao,
) {

    fun getTasks(): Flow<List<TaskFullDb>> {
        logi(TAG, "Get all flow order by update date")
        return dao.getAllFlowOrderUpdateDateDesc()
    }

    suspend fun insertTask(model: TaskDb): Long {
        logi(TAG, "Insert item: $model")
        return dao.insert(model)
    }

    suspend fun insertTasks(list: List<TaskDb>) {
        logi(TAG, "Insert list: ${list.count()}")
        return dao.insert(list)
    }

    suspend fun updateTask(model: TaskDb) {
        logi(TAG, "Update item: $model")
        return dao.update(model)
    }

    suspend fun deleteTask(model: TaskDb) {
        logi(TAG, "Delete item: $model")
        return dao.delete(model)
    }

    companion object {

        private const val TAG = "TasksLocalDS"
    }
}
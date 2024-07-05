package com.emikhalets.superapp.data.tasks.table_tasks

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.emikhalets.superapp.core.database.AppDao
import com.emikhalets.superapp.data.tasks.embeded.TaskFullDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao : AppDao<TaskDb> {

    @Query("DELETE FROM tasks")
    suspend fun drop()

    @Query("DELETE FROM tasks WHERE parent_id = :id")
    suspend fun deleteByParentId(id: Long)

    @Transaction
    @Query("SELECT * FROM tasks WHERE parent_id = 0 ORDER BY update_date DESC")
    fun getAllFlow(): Flow<List<TaskFullDb>>
}
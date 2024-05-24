package com.emikhalets.superapp.data.notes.table_tasks

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.emikhalets.superapp.core.database.AppDao
import com.emikhalets.superapp.data.notes.embeded.TaskFullDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao : AppDao<TaskDb> {

    @Query("DELETE FROM tasks")
    suspend fun drop()

    @Query("DELETE FROM tasks WHERE parent_id = :id")
    suspend fun deleteByParentId(id: Long)

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY update_date DESC")
    fun getAllFlow(): Flow<List<TaskFullDb>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getItem(id: Long): TaskFullDb
}
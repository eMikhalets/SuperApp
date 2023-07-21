package com.emikhalets.core.database.notes.table_tasks

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.emikhalets.core.database.AppDao
import com.emikhalets.core.database.notes.embeded.TaskFullDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao : AppDao<TaskDb> {

    @Query("DELETE FROM tasks")
    suspend fun drop()

    @Query("SELECT * FROM tasks")
    suspend fun getAll(): List<TaskDb>

    @Query("SELECT * FROM tasks")
    fun getAllFlow(): Flow<List<TaskDb>>

    // TODO: request tasks and completed tasks separate. sort by update_date DESC, completed ASC
    @Transaction
    @Query("SELECT * FROM tasks WHERE parent_id == 0 ORDER BY update_date DESC")
    fun getAllFlowOrderUpdateDateDesc(): Flow<List<TaskFullDb>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getItem(id: Long): TaskDb

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getItemFlow(id: Long): Flow<TaskDb>
}
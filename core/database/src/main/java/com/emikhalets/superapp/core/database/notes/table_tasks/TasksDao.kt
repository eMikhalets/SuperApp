package com.emikhalets.superapp.core.database.notes.table_tasks

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.emikhalets.superapp.core.database.AppDao
import com.emikhalets.superapp.core.database.notes.embeded.TaskFullDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao : AppDao<TaskDb> {

    @Query("DELETE FROM tasks")
    suspend fun drop()

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY create_date DESC")
    fun getAllFlow(): Flow<List<TaskFullDb>>
}
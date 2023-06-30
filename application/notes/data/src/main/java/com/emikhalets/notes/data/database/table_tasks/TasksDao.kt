package com.emikhalets.notes.data.database.table_tasks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.emikhalets.notes.data.database.embeded.TaskWithSubtasksDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert
    suspend fun insert(entity: TaskDb): Long

    @Update
    suspend fun update(entity: TaskDb)

    @Update
    suspend fun update(entities: List<TaskDb>)

    @Delete
    suspend fun delete(entity: TaskDb)

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY create_timestamp DESC")
    fun getAllFlow(): Flow<List<TaskWithSubtasksDb>>
}
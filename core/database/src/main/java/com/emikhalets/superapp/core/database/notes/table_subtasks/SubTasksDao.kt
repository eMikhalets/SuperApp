package com.emikhalets.superapp.core.database.notes.table_subtasks

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.superapp.core.database.AppDao

@Dao
interface SubTasksDao : AppDao<SubTaskDb> {

    @Query("DELETE FROM subtasks")
    suspend fun drop()

    @Query("DELETE FROM subtasks WHERE parent_id = :id")
    suspend fun deleteByParentId(id: Long)
}
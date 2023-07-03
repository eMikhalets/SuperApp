package com.emikhalets.notes.data.database.table_subtasks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface SubtasksDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SubtaskDb)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entities: List<SubtaskDb>)

    @Update
    suspend fun update(entity: SubtaskDb)

    @Update
    suspend fun update(entities: List<SubtaskDb>)

    @Delete
    suspend fun delete(entity: SubtaskDb)
}
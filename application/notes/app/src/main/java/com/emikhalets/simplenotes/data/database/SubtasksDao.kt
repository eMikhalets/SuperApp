package com.emikhalets.simplenotes.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.emikhalets.simplenotes.data.database.entities.SubtaskDb

@Dao
interface SubtasksDao {

    @Insert
    suspend fun insert(entity: SubtaskDb)

    @Update
    suspend fun update(entity: SubtaskDb)

    @Update
    suspend fun update(entities: List<SubtaskDb>)

    @Delete
    suspend fun delete(entity: SubtaskDb)
}
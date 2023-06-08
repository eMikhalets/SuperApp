package com.emikhalets.notes.data.database.table_notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert
    suspend fun insert(entity: NoteDb)

    @Update
    suspend fun update(entity: NoteDb)

    @Delete
    suspend fun delete(entity: NoteDb)

    @Query("SELECT * FROM notes ORDER BY create_timestamp DESC")
    fun getAllFlow(): Flow<List<NoteDb>>
}
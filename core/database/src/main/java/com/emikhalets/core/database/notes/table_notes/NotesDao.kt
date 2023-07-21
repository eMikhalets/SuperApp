package com.emikhalets.core.database.notes.table_notes

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao : AppDao<NoteDb> {

    @Query("DELETE FROM notes")
    suspend fun drop()

    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<NoteDb>

    @Query("SELECT * FROM notes")
    fun getAllFlow(): Flow<List<NoteDb>>

    @Query("SELECT * FROM notes ORDER BY update_date DESC")
    fun getAllFlowOrderUpdateDateDesc(): Flow<List<NoteDb>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getItem(id: Long): NoteDb

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getItemFlow(id: Long): Flow<NoteDb>
}
package com.emikhalets.core.database.notes

import com.emikhalets.core.database.notes.embeded.TaskFullDb
import com.emikhalets.core.database.notes.table_notes.NoteDb
import com.emikhalets.core.database.notes.table_notes.NotesDao
import com.emikhalets.core.database.notes.table_tasks.TaskDb
import com.emikhalets.core.database.notes.table_tasks.TasksDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalNotesDataSource @Inject constructor(
    private val notesDao: NotesDao,
    private val tasksDao: TasksDao,
) {

    fun getNotes(): Flow<List<NoteDb>> {
        return notesDao.getAllFlowOrderUpdateDateDesc()
    }

    suspend fun insertNote(model: NoteDb): Long {
        return notesDao.insert(model)
    }

    suspend fun insertNotes(list: List<NoteDb>) {
        return notesDao.insert(list)
    }

    suspend fun updateNote(model: NoteDb) {
        return notesDao.update(model)
    }

    suspend fun deleteNote(model: NoteDb) {
        return notesDao.delete(model)
    }

    fun getTasks(): Flow<List<TaskFullDb>> {
        return tasksDao.getAllFlowOrderUpdateDateDesc()
    }

    suspend fun insertTask(model: TaskDb): Long {
        return tasksDao.insert(model)
    }

    suspend fun insertTasks(list: List<TaskDb>) {
        return tasksDao.insert(list)
    }

    suspend fun updateTask(model: TaskDb) {
        return tasksDao.update(model)
    }

    suspend fun deleteTask(model: TaskDb) {
        return tasksDao.delete(model)
    }
}
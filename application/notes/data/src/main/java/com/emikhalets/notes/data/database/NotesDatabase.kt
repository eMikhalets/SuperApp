package com.emikhalets.notes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.core.common.logi
import com.emikhalets.notes.data.database.table_notes.NoteDb
import com.emikhalets.notes.data.database.table_notes.NotesDao
import com.emikhalets.notes.data.database.table_subtasks.SubtaskDb
import com.emikhalets.notes.data.database.table_subtasks.SubtasksDao
import com.emikhalets.notes.data.database.table_tasks.TaskDb
import com.emikhalets.notes.data.database.table_tasks.TasksDao

@Database(
    entities = [
        NoteDb::class,
        TaskDb::class,
        SubtaskDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val notesDao: NotesDao
    abstract val tasksDao: TasksDao
    abstract val subtasksDao: SubtasksDao

    companion object {

        private const val TAG = "NotesDatabase"

        @Volatile
        private var instance: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            logi(TAG, "Get instance")
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): NotesDatabase {
            logi(TAG, "Building database")
            return Room
                .databaseBuilder(context, NotesDatabase::class.java, "AppNotes.db")
                .build()
        }
    }
}
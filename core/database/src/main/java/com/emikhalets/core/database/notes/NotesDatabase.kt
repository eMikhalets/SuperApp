package com.emikhalets.core.database.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.core.common.logi
import com.emikhalets.core.database.notes.table_notes.NoteDb
import com.emikhalets.core.database.notes.table_notes.NotesDao
import com.emikhalets.core.database.notes.table_tasks.TaskDb
import com.emikhalets.core.database.notes.table_tasks.TasksDao

@Database(
    entities = [
        NoteDb::class,
        TaskDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val notesDao: NotesDao
    abstract val tasksDao: TasksDao

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
                .databaseBuilder(context, NotesDatabase::class.java, "Notes.db")
                .build()
        }
    }
}
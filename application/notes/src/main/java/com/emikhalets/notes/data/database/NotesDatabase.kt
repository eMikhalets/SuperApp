package com.emikhalets.notes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.notes.data.database.table_notes.NoteDb
import com.emikhalets.notes.data.database.table_notes.NotesDao
import com.emikhalets.notes.data.database.table_tasks.TaskDb
import com.emikhalets.notes.data.database.table_tasks.TasksDao

@Database(
    entities = [
        TaskDb::class,
        NoteDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val tasksDao: TasksDao
    abstract val notesDao: NotesDao

    companion object {

        @Volatile
        private var instance: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): NotesDatabase {
            return Room
                .databaseBuilder(context, NotesDatabase::class.java, "Notes.db")
                .build()
        }
    }
}
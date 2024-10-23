package com.emikhalets.superapp.core.database.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.superapp.core.database.notes.table_subtasks.SubTaskDb
import com.emikhalets.superapp.core.database.notes.table_subtasks.SubTasksDao
import com.emikhalets.superapp.core.database.notes.table_tasks.TaskDb
import com.emikhalets.superapp.core.database.notes.table_tasks.TasksDao

@Database(
    entities = [
        TaskDb::class,
        SubTaskDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val tasksDao: TasksDao
    abstract val subTasksDao: SubTasksDao

    companion object {

        @Volatile
        private var instance: NotesDatabase? = null
        private const val NAME: String = "Notes.db"

        fun getInstance(context: Context): NotesDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): NotesDatabase {
            return Room
                .databaseBuilder(context, NotesDatabase::class.java, NAME)
                .build()
        }
    }
}

package com.emikhalets.superapp.data.tasks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.superapp.data.tasks.table_tasks.TaskDb
import com.emikhalets.superapp.data.tasks.table_tasks.TasksDao

@Database(
    entities = [
        TaskDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class TasksDatabase : RoomDatabase() {

    abstract val tasksDao: TasksDao

    companion object {

        @Volatile
        private var instance: TasksDatabase? = null
        private const val NAME: String = "Tasks.db"

        fun getInstance(context: Context): TasksDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): TasksDatabase {
            return Room
                .databaseBuilder(context, TasksDatabase::class.java, NAME)
                .build()
        }
    }
}

package com.emikhalets.fitness.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [WorkoutDB::class],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class FitnessDatabase : RoomDatabase() {

    abstract val workoutsDao: WorkoutsDao

    companion object {

        @Volatile
        private var instance: FitnessDatabase? = null

        fun getInstance(context: Context): FitnessDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): FitnessDatabase {
            return Room
                .databaseBuilder(context, FitnessDatabase::class.java, "Fitness.db")
                .build()
        }
    }
}

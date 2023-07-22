package com.emikhalets.core.database.workout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.core.common.logi
import com.emikhalets.core.database.workout.table_exercises.ExercisesDao
import com.emikhalets.core.database.workout.table_exercises.ExercisesDb
import com.emikhalets.core.database.workout.table_programs.ProgramDb
import com.emikhalets.core.database.workout.table_programs.ProgramsDao
import com.emikhalets.core.database.workout.table_workouts.WorkoutDb
import com.emikhalets.core.database.workout.table_workouts.WorkoutsDao

@Database(
    entities = [
        ExercisesDb::class,
        ProgramDb::class,
        WorkoutDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class WorkoutDatabase : RoomDatabase() {

    abstract val exercisesDao: ExercisesDao
    abstract val programsDao: ProgramsDao
    abstract val workoutsDao: WorkoutsDao

    companion object {

        private const val TAG = "WorkoutDatabase"

        @Volatile
        private var instance: WorkoutDatabase? = null

        fun getInstance(context: Context): WorkoutDatabase {
            logi(TAG, "Get instance")
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): WorkoutDatabase {
            logi(TAG, "Building database")
            return Room
                .databaseBuilder(context, WorkoutDatabase::class.java, "Workout.db")
                .build()
        }
    }
}
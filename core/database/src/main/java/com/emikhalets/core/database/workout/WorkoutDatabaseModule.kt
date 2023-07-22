package com.emikhalets.core.database.workout

import android.content.Context
import com.emikhalets.core.database.workout.table_exercises.ExercisesDao
import com.emikhalets.core.database.workout.table_programs.ProgramsDao
import com.emikhalets.core.database.workout.table_workouts.WorkoutsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WorkoutDatabaseModule {

    @Provides
    fun providesWorkoutDatabase(@ApplicationContext context: Context): WorkoutDatabase {
        return WorkoutDatabase.getInstance(context)
    }

    @Provides
    fun providesExercisesDao(database: WorkoutDatabase): ExercisesDao {
        return database.exercisesDao
    }

    @Provides
    fun providesProgramsDao(database: WorkoutDatabase): ProgramsDao {
        return database.programsDao
    }

    @Provides
    fun providesWorkoutsDao(database: WorkoutDatabase): WorkoutsDao {
        return database.workoutsDao
    }
}

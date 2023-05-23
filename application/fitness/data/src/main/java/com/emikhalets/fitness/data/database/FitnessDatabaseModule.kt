package com.emikhalets.fitness.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FitnessDatabaseModule {

    @Singleton
    @Provides
    fun provideFitnessDatabase(@ApplicationContext context: Context): FitnessDatabase {
        return Room
            .databaseBuilder(context, FitnessDatabase::class.java, "Fitness.db")
            .build()
    }

    @Singleton
    @Provides
    fun providesWorkoutsDao(database: FitnessDatabase): WorkoutsDao {
        return database.workoutsDao
    }
}

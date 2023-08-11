package com.emikhalets.fitness.data.database

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FitnessDatabaseModule {

    @Provides
    fun provideFitnessDatabase(@ApplicationContext context: Context): FitnessDatabase {
        return FitnessDatabase.getInstance(context)
    }

    @Provides
    fun providesWorkoutsDao(database: FitnessDatabase): WorkoutsDao {
        return database.workoutsDao
    }
}

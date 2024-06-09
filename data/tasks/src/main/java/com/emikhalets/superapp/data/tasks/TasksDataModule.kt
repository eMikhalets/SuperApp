package com.emikhalets.superapp.data.tasks

import android.content.Context
import com.emikhalets.superapp.data.tasks.table_tasks.TasksDao
import com.emikhalets.superapp.domain.tasks.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object TasksDataModule {

    @Provides
    fun provideTasksDatabase(@ApplicationContext context: Context): TasksDatabase {
        return TasksDatabase.getInstance(context)
    }

    @Provides
    fun providesTasksDao(database: TasksDatabase): TasksDao {
        return database.tasksDao
    }

    @Provides
    fun provideSalaryRepository(tasksDao: TasksDao): TasksRepository {
        return TasksRepositoryImpl(tasksDao)
    }
}

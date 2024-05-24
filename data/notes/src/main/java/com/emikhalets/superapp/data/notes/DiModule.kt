package com.emikhalets.superapp.data.notes

import android.content.Context
import com.emikhalets.superapp.data.notes.table_tasks.TasksDao
import com.emikhalets.superapp.domain.notes.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DiModule {

    @Provides
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase {
        return NotesDatabase.getInstance(context)
    }

    @Provides
    fun providesTasksDao(database: NotesDatabase): TasksDao {
        return database.tasksDao
    }

    @Provides
    fun provideSalaryRepository(tasksDao: TasksDao): NotesRepository {
        return NotesRepositoryImpl(tasksDao)
    }
}

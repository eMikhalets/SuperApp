package com.emikhalets.superapp.feature.notes

import android.content.Context
import com.emikhalets.superapp.core.database.notes.NotesDatabase
import com.emikhalets.superapp.core.database.notes.table_subtasks.SubTasksDao
import com.emikhalets.superapp.core.database.notes.table_tasks.TasksDao
import com.emikhalets.superapp.feature.notes.data.TasksRepositoryImpl
import com.emikhalets.superapp.feature.notes.domain.TasksRepository
import com.emikhalets.superapp.feature.notes.domain.use_case.DeleteTaskUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.GetTasksUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.InsertTaskUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object NotesModule {

    @Provides
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase {
        return NotesDatabase.getInstance(context)
    }

    @Provides
    fun providesTasksDao(database: NotesDatabase): TasksDao {
        return database.tasksDao
    }

    @Provides
    fun providesSubTasksDao(database: NotesDatabase): SubTasksDao {
        return database.subTasksDao
    }

    @Provides
    fun provideNotesRepository(tasksDao: TasksDao, subTasksDao: SubTasksDao): TasksRepository {
        return TasksRepositoryImpl(tasksDao, subTasksDao)
    }

    @Provides
    fun provideDeleteTaskUseCase(tasksRepository: TasksRepository): DeleteTaskUseCase {
        return DeleteTaskUseCase(tasksRepository)
    }

    @Provides
    fun provideGetTasksUseCase(tasksRepository: TasksRepository): GetTasksUseCase {
        return GetTasksUseCase(tasksRepository)
    }

    @Provides
    fun provideInsertTaskUseCase(tasksRepository: TasksRepository): InsertTaskUseCase {
        return InsertTaskUseCase(tasksRepository)
    }

    @Provides
    fun provideUpdateTaskUseCase(tasksRepository: TasksRepository): UpdateTaskUseCase {
        return UpdateTaskUseCase(tasksRepository)
    }
}

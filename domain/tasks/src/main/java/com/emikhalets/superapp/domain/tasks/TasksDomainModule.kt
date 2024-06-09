package com.emikhalets.superapp.domain.tasks

import com.emikhalets.superapp.domain.tasks.use_case.DeleteTaskUseCase
import com.emikhalets.superapp.domain.tasks.use_case.GetTasksUseCase
import com.emikhalets.superapp.domain.tasks.use_case.InsertTaskUseCase
import com.emikhalets.superapp.domain.tasks.use_case.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TasksDomainModule {

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

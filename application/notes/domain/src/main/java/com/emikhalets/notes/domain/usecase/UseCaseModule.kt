package com.emikhalets.notes.domain.usecase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindsNotesUseCase(impl: NotesUseCaseImpl): NotesUseCase

    @Binds
    abstract fun bindsTasksUseCase(impl: TasksUseCaseImpl): TasksUseCase

    @Binds
    abstract fun bindsSubtasksUseCase(impl: SubtasksUseCaseImpl): SubtasksUseCase
}

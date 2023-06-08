package com.emikhalets.notes.data.repository

import com.emikhalets.notes.domain.repository.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotesRepositoryModule {

    @Binds
    abstract fun bindsNotesRepository(impl: NotesRepositoryImpl): NotesRepository
}

package com.emikhalets.events.data.repository

import com.emikhalets.events.domain.repository.EventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class EventsRepositoryModule {

    @Binds
    abstract fun bindsEventsRepository(impl: EventsRepositoryImpl): EventsRepository
}

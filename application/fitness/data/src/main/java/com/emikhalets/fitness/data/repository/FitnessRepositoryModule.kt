package com.emikhalets.fitness.data.repository

import com.emikhalets.fitness.domain.repository.FitnessRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FitnessRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsFitnessRepository(impl: FitnessRepositoryImpl): FitnessRepository
}

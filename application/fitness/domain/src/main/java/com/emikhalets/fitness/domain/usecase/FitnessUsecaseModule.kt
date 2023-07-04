package com.emikhalets.fitness.domain.usecase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FitnessUsecaseModule {

    @Binds
    abstract fun bindsProgramsUsecase(impl: ProgramsUseCaseImpl): ProgramsUseCase
}

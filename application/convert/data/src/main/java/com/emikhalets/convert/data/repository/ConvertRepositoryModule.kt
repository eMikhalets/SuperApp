package com.emikhalets.convert.data.repository

import com.emikhalets.convert.domain.repository.ConvertRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ConvertRepositoryModule {

    @Binds
    abstract fun bindsConvertRepository(impl: ConvertRepositoryImpl): ConvertRepository
}

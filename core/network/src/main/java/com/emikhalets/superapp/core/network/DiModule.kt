package com.emikhalets.superapp.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DiModule {

    @Provides
    fun providesCurrencyParser(): CurrencyParser {
        return CurrencyParser()
    }
}

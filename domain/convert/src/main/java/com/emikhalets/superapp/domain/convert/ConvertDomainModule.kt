package com.emikhalets.superapp.domain.convert

import com.emikhalets.superapp.domain.convert.use_case.ConvertCurrencyUseCase
import com.emikhalets.superapp.domain.convert.use_case.DeleteCurrencyUseCase
import com.emikhalets.superapp.domain.convert.use_case.GetCurrenciesUseCase
import com.emikhalets.superapp.domain.convert.use_case.GetExchangesUseCase
import com.emikhalets.superapp.domain.convert.use_case.InsertCurrencyUseCase
import com.emikhalets.superapp.domain.convert.use_case.UpdateExchangesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConvertDomainModule {

    @Provides
    fun provideConvertCurrencyUseCase(): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase()
    }

    @Provides
    fun provideDeleteCurrencyUseCase(convertRepository: ConvertRepository): DeleteCurrencyUseCase {
        return DeleteCurrencyUseCase(convertRepository)
    }

    @Provides
    fun provideGetCurrenciesUseCase(convertRepository: ConvertRepository): GetCurrenciesUseCase {
        return GetCurrenciesUseCase(convertRepository)
    }

    @Provides
    fun provideGetExchangesUseCase(convertRepository: ConvertRepository): GetExchangesUseCase {
        return GetExchangesUseCase(convertRepository)
    }

    @Provides
    fun provideInsertCurrencyUseCase(convertRepository: ConvertRepository): InsertCurrencyUseCase {
        return InsertCurrencyUseCase(convertRepository)
    }

    @Provides
    fun provideUpdateExchangesUseCase(convertRepository: ConvertRepository): UpdateExchangesUseCase {
        return UpdateExchangesUseCase(convertRepository)
    }
}

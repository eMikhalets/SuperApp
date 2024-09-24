package com.emikhalets.superapp.feature.convert

import android.content.Context
import com.emikhalets.superapp.core.database.convert.ConvertDatabase
import com.emikhalets.superapp.core.database.convert.table_exchanges.ExchangesDao
import com.emikhalets.superapp.core.network.CurrencyParser
import com.emikhalets.superapp.feature.convert.data.ConvertRepositoryImpl
import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.use_case.ConvertCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.DeleteCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.GetExchangesUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.InsertCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.UpdateExchangesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConvertModule {

    @Provides
    fun providesConvertDatabase(@ApplicationContext context: Context): ConvertDatabase {
        return ConvertDatabase.getInstance(context)
    }

    @Provides
    fun providesExchangesDao(database: ConvertDatabase): ExchangesDao {
        return database.exchangesDao
    }

    @Provides
    fun provideConvertRepository(
        currencyParser: CurrencyParser,
        exchangesDao: ExchangesDao,
    ): ConvertRepository {
        return ConvertRepositoryImpl(currencyParser, exchangesDao)
    }

    @Provides
    fun provideConvertCurrencyUseCase(): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase()
    }

    @Provides
    fun provideDeleteCurrencyUseCase(convertRepository: ConvertRepository): DeleteCurrencyUseCase {
        return DeleteCurrencyUseCase(convertRepository)
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

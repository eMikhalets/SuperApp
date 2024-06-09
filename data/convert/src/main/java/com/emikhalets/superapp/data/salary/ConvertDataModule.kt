package com.emikhalets.superapp.data.salary

import android.content.Context
import com.emikhalets.superapp.core.network.CurrencyParser
import com.emikhalets.superapp.data.salary.table_currencies.CurrenciesDao
import com.emikhalets.superapp.data.salary.table_currency_pair.CurrencyPairDao
import com.emikhalets.superapp.domain.convert.ConvertRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConvertDataModule {

    @Provides
    fun providesConvertDatabase(@ApplicationContext context: Context): ConvertDatabase {
        return ConvertDatabase.getInstance(context)
    }

    @Provides
    fun providesCurrenciesDao(database: ConvertDatabase): CurrenciesDao {
        return database.currenciesDao
    }

    @Provides
    fun providesCurrencyPairDao(database: ConvertDatabase): CurrencyPairDao {
        return database.currencyPairDao
    }

    @Provides
    fun provideConvertRepository(
        currencyParser: CurrencyParser,
        currenciesDao: CurrenciesDao,
        currencyPairDao: CurrencyPairDao,
    ): ConvertRepository {
        return ConvertRepositoryImpl(currencyParser, currenciesDao, currencyPairDao)
    }
}

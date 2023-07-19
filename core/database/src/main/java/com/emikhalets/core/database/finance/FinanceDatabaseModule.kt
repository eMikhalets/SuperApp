package com.emikhalets.core.database.finance

import android.content.Context
import com.emikhalets.core.database.finance.table_currencies.CurrenciesDao
import com.emikhalets.core.database.finance.table_exchanges.ExchangesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FinanceDatabaseModule {

    @Provides
    fun providesFinanceDatabase(@ApplicationContext context: Context): FinanceDatabase {
        return FinanceDatabase.getInstance(context)
    }

    @Provides
    fun providesCurrenciesDao(database: FinanceDatabase): CurrenciesDao {
        return database.currenciesDao
    }

    @Provides
    fun providesExchangesDao(database: FinanceDatabase): ExchangesDao {
        return database.exchangesDao
    }
}

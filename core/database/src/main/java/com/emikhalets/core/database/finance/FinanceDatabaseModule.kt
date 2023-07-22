package com.emikhalets.core.database.finance

import android.content.Context
import com.emikhalets.core.database.finance.table_category.CategoriesDao
import com.emikhalets.core.database.finance.table_convert_currencies.ConvertCurrenciesDao
import com.emikhalets.core.database.finance.table_currencies.CurrenciesDao
import com.emikhalets.core.database.finance.table_exchanges.ExchangesDao
import com.emikhalets.core.database.finance.table_transactions.TransactionsDao
import com.emikhalets.core.database.finance.table_wallets.WalletsDao
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
    fun providesCategoriesDao(database: FinanceDatabase): CategoriesDao {
        return database.categoriesDao
    }

    @Provides
    fun providesConvertCurrenciesDao(database: FinanceDatabase): ConvertCurrenciesDao {
        return database.convertCurrenciesDao
    }

    @Provides
    fun providesCurrenciesDao(database: FinanceDatabase): CurrenciesDao {
        return database.currenciesDao
    }

    @Provides
    fun providesExchangesDao(database: FinanceDatabase): ExchangesDao {
        return database.exchangesDao
    }

    @Provides
    fun providesTransactionsDao(database: FinanceDatabase): TransactionsDao {
        return database.transactionsDao
    }

    @Provides
    fun providesWalletsDao(database: FinanceDatabase): WalletsDao {
        return database.walletsDao
    }
}

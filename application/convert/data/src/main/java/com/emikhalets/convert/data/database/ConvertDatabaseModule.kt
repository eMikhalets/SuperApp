package com.emikhalets.convert.data.database

import android.content.Context
import com.emikhalets.convert.data.database.table_exchanges.ExchangesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConvertDatabaseModule {

    @Provides
    fun provideConvertDatabase(@ApplicationContext context: Context): ConvertDatabase {
        return ConvertDatabase.getInstance(context)
    }

    @Provides
    fun providesExchangesDao(database: ConvertDatabase): ExchangesDao {
        return database.exchangesDao
    }
}

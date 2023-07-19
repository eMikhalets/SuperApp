package com.emikhalets.core.database.finance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.core.common.logi
import com.emikhalets.core.database.finance.table_currencies.CurrenciesDao
import com.emikhalets.core.database.finance.table_currencies.CurrencyDb
import com.emikhalets.core.database.finance.table_exchanges.ExchangeDb
import com.emikhalets.core.database.finance.table_exchanges.ExchangesDao

@Database(
    entities = [CurrencyDb::class, ExchangeDb::class],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class FinanceDatabase : RoomDatabase() {

    abstract val currenciesDao: CurrenciesDao
    abstract val exchangesDao: ExchangesDao

    companion object {

        private const val TAG = "FinanceDatabase"

        @Volatile
        private var instance: FinanceDatabase? = null

        fun getInstance(context: Context): FinanceDatabase {
            logi(TAG, "Get instance")
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): FinanceDatabase {
            logi(TAG, "Building database")
            return Room
                .databaseBuilder(context, FinanceDatabase::class.java, "Finance.db")
                .build()
        }
    }
}
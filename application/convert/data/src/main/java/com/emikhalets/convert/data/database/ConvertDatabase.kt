package com.emikhalets.convert.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.convert.data.database.table_currencies.CurrenciesDao
import com.emikhalets.convert.data.database.table_currencies.CurrencyDb
import com.emikhalets.convert.data.database.table_exchanges.ExchangeDb
import com.emikhalets.convert.data.database.table_exchanges.ExchangesDao
import com.emikhalets.core.common.logi

@Database(
    entities = [CurrencyDb::class, ExchangeDb::class],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class ConvertDatabase : RoomDatabase() {

    abstract val currenciesDao: CurrenciesDao
    abstract val exchangesDao: ExchangesDao

    companion object {

        private const val TAG = "ConvertDatabase"

        @Volatile
        private var instance: ConvertDatabase? = null

        fun getInstance(context: Context): ConvertDatabase {
            logi(TAG, "Get instance")
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): ConvertDatabase {
            logi(TAG, "Building database")
            return Room
                .databaseBuilder(context, ConvertDatabase::class.java, "AppConvert.db")
                .build()
        }
    }
}
package com.emikhalets.core.database.convert

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.core.database.convert.table_currencies.CurrenciesDao
import com.emikhalets.core.database.convert.table_currencies.CurrencyDb
import com.emikhalets.core.database.convert.table_exchanges.ExchangeDb
import com.emikhalets.core.database.convert.table_exchanges.ExchangesDao

@Database(
    entities = [
        CurrencyDb::class,
        ExchangeDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class ConvertDatabase : RoomDatabase() {

    abstract val currenciesDao: CurrenciesDao
    abstract val exchangesDao: ExchangesDao

    companion object {

        @Volatile
        private var instance: ConvertDatabase? = null

        fun getInstance(context: Context): ConvertDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): ConvertDatabase {
            return Room
                .databaseBuilder(context, ConvertDatabase::class.java, "Convert.db")
                .build()
        }
    }
}

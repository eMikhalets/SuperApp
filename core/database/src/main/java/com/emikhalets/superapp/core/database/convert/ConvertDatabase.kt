package com.emikhalets.superapp.core.database.convert

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.superapp.core.database.convert.table_exchanges.ExchangeDb
import com.emikhalets.superapp.core.database.convert.table_exchanges.ExchangesDao

@Database(
    entities = [
        ExchangeDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class ConvertDatabase : RoomDatabase() {

    abstract val exchangesDao: ExchangesDao

    companion object {

        @Volatile
        private var instance: ConvertDatabase? = null
        private const val NAME: String = "Convert.db"

        fun getInstance(context: Context): ConvertDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ConvertDatabase {
            return Room
                .databaseBuilder(context, ConvertDatabase::class.java, NAME)
                .build()
        }
    }
}

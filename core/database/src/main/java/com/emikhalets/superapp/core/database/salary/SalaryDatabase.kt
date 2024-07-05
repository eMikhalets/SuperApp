package com.emikhalets.superapp.core.database.salary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.superapp.core.database.salary.table_salaries.SalariesDao
import com.emikhalets.superapp.core.database.salary.table_salaries.SalaryDb

@Database(
    entities = [
        SalaryDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class SalaryDatabase : RoomDatabase() {

    abstract val salariesDao: SalariesDao

    companion object {

        @Volatile
        private var instance: SalaryDatabase? = null
        private const val NAME: String = "Salary.db"

        fun getInstance(context: Context): SalaryDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): SalaryDatabase {
            return Room
                .databaseBuilder(context, SalaryDatabase::class.java, NAME)
                .build()
        }
    }
}

package com.emikhalets.core.database.salary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.core.database.salary.table_salaries.SalariesDao
import com.emikhalets.core.database.salary.table_salaries.SalaryDb

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

        fun getInstance(context: Context): SalaryDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): SalaryDatabase {
            return Room
                .databaseBuilder(context, SalaryDatabase::class.java, "Salary.db")
                .build()
        }
    }
}

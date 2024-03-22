package com.emikhalets.core.database.salary

import android.content.Context
import com.emikhalets.core.database.salary.table_salaries.SalariesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SalaryDatabaseModule {

    @Provides
    fun provideSalaryDatabase(@ApplicationContext context: Context): SalaryDatabase {
        return SalaryDatabase.getInstance(context)
    }

    @Provides
    fun provideSalariesDao(database: SalaryDatabase): SalariesDao {
        return database.salariesDao
    }
}

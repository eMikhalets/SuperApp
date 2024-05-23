package com.emikhalets.superapp.data.salary

import android.content.Context
import com.emikhalets.superapp.domain.salary.repository.SalaryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DiModule {

    @Provides
    fun provideSalaryDatabase(@ApplicationContext context: Context): SalaryDatabase {
        return SalaryDatabase.getInstance(context)
    }

    @Provides
    fun provideSalariesDao(database: SalaryDatabase): SalariesDao {
        return database.salariesDao
    }

    @Provides
    fun provideSalaryRepository(salariesDao: SalariesDao): SalaryRepository {
        return SalaryRepositoryImpl(salariesDao)
    }
}

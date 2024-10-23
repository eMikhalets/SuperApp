package com.emikhalets.superapp.feature.salary

import android.content.Context
import com.emikhalets.superapp.core.database.salary.SalaryDatabase
import com.emikhalets.superapp.core.database.salary.table_salaries.SalariesDao
import com.emikhalets.superapp.feature.salary.data.SalaryRepositoryImpl
import com.emikhalets.superapp.feature.salary.domain.SalaryRepository
import com.emikhalets.superapp.feature.salary.domain.use_case.DeleteSalaryUseCase
import com.emikhalets.superapp.feature.salary.domain.use_case.GetSalariesUseCase
import com.emikhalets.superapp.feature.salary.domain.use_case.InsertSalaryUseCase
import com.emikhalets.superapp.feature.salary.domain.use_case.UpdateSalaryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SalaryModule {

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

    @Provides
    fun provideDeleteSalaryUseCase(salaryRepository: SalaryRepositoryImpl): DeleteSalaryUseCase {
        return DeleteSalaryUseCase(salaryRepository)
    }

    @Provides
    fun provideGetSalariesUseCase(salaryRepository: SalaryRepositoryImpl): GetSalariesUseCase {
        return GetSalariesUseCase(salaryRepository)
    }

    @Provides
    fun provideInsertSalaryUseCase(salaryRepository: SalaryRepositoryImpl): InsertSalaryUseCase {
        return InsertSalaryUseCase(salaryRepository)
    }

    @Provides
    fun provideUpdateSalaryUseCase(salaryRepository: SalaryRepositoryImpl): UpdateSalaryUseCase {
        return UpdateSalaryUseCase(salaryRepository)
    }
}

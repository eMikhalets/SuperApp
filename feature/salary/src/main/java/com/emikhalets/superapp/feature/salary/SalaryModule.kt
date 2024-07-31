package com.emikhalets.superapp.feature.salary

import android.content.Context
import com.emikhalets.superapp.core.database.salary.SalaryDatabase
import com.emikhalets.superapp.core.database.salary.table_salaries.SalariesDao
import com.emikhalets.superapp.feature.salary.data.SalaryRepository
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
        return SalaryRepository(salariesDao)
    }

    @Provides
    fun provideDeleteSalaryUseCase(salaryRepository: SalaryRepository): DeleteSalaryUseCase {
        return DeleteSalaryUseCase(salaryRepository)
    }

    @Provides
    fun provideGetSalariesUseCase(salaryRepository: SalaryRepository): GetSalariesUseCase {
        return GetSalariesUseCase(salaryRepository)
    }

    @Provides
    fun provideInsertSalaryUseCase(salaryRepository: SalaryRepository): InsertSalaryUseCase {
        return InsertSalaryUseCase(salaryRepository)
    }

    @Provides
    fun provideUpdateSalaryUseCase(salaryRepository: SalaryRepository): UpdateSalaryUseCase {
        return UpdateSalaryUseCase(salaryRepository)
    }
}

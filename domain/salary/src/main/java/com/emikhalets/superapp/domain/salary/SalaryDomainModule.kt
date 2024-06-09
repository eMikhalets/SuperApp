package com.emikhalets.superapp.domain.salary

import com.emikhalets.superapp.domain.salary.use_case.DeleteSalaryUseCase
import com.emikhalets.superapp.domain.salary.use_case.GetSalariesUseCase
import com.emikhalets.superapp.domain.salary.use_case.GetSalaryUseCase
import com.emikhalets.superapp.domain.salary.use_case.InsertSalaryUseCase
import com.emikhalets.superapp.domain.salary.use_case.UpdateSalaryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SalaryDomainModule {

    @Provides
    fun provideDeleteSalaryUseCase(salaryRepository: SalaryRepository): DeleteSalaryUseCase {
        return DeleteSalaryUseCase(salaryRepository)
    }

    @Provides
    fun provideGetSalariesUseCase(salaryRepository: SalaryRepository): GetSalariesUseCase {
        return GetSalariesUseCase(salaryRepository)
    }

    @Provides
    fun provideGetSalaryUseCase(salaryRepository: SalaryRepository): GetSalaryUseCase {
        return GetSalaryUseCase(salaryRepository)
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

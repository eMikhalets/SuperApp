package com.emikhalets.superapp.feature.salary.domain

import com.emikhalets.superapp.core.common.AppResult
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {

    fun getSalaries(): Flow<List<SalaryModel>>

    suspend fun addSalary(model: SalaryModel): AppResult<Boolean>

    suspend fun updateSalary(model: SalaryModel): AppResult<Boolean>

    suspend fun deleteSalary(model: SalaryModel): AppResult<Boolean>
}
package com.emikhalets.superapp.domain.salary

import com.emikhalets.superapp.core.common.AppResult
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {

    fun getSalaries(): Flow<List<SalaryModel>>

    suspend fun getSalary(id: Long): AppResult<SalaryModel>

    suspend fun addSalary(model: SalaryModel): AppResult<Long>

    suspend fun updateSalary(model: SalaryModel): AppResult<Int>

    suspend fun deleteSalary(model: SalaryModel): AppResult<Int>

    suspend fun deleteAllSalaries()
}
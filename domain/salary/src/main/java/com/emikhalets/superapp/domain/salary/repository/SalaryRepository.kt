package com.emikhalets.superapp.domain.salary.repository

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.domain.salary.model.SalaryModel
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {

    fun getSalaries(): Flow<List<SalaryModel>>

    suspend fun getSalary(id: Long): AppResult<SalaryModel>

    suspend fun addSalary(model: SalaryModel): AppResult<Long>

    suspend fun updateSalary(model: SalaryModel): AppResult<Int>

    suspend fun deleteSalary(model: SalaryModel): AppResult<Int>

    suspend fun deleteAllSalaries()
}
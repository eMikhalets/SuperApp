package com.emikhalets.core.database.salary

import com.emikhalets.core.database.salary.table_salaries.SalariesDao
import com.emikhalets.core.database.salary.table_salaries.SalaryDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SalaryLocalDataSource @Inject constructor(
    private val salariesDao: SalariesDao,
) {

    fun getSalariesFlow(): Flow<List<SalaryDb>> {
        return salariesDao.getAllFlow()
    }

    suspend fun insertSalary(model: SalaryDb): Long {
        return salariesDao.insert(model)
    }

    suspend fun updateSalary(model: SalaryDb): Int {
        return salariesDao.update(model)
    }

    suspend fun deleteSalary(model: SalaryDb): Int {
        return salariesDao.delete(model)
    }

    suspend fun dropSalaries() {
        return salariesDao.drop()
    }
}
package com.emikhalets.superapp.feature.salary.data

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.date.DateHelper
import com.emikhalets.superapp.core.common.invoke
import com.emikhalets.superapp.core.database.salary.table_salaries.SalariesDao
import com.emikhalets.superapp.core.database.salary.table_salaries.SalaryDb
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import com.emikhalets.superapp.feature.salary.domain.SalaryType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject

class SalaryRepository @Inject constructor(
    private val salariesDao: SalariesDao,
) {

    fun getSalaries(): Flow<List<SalaryModel>> {
        Timber.d("getSalaries()")
//        val result = salariesDao.getAllFlow()
        val result = flowOf(
            listOf(
                SalaryDb(1, 6000000, DateHelper.timestampOf(15, 5, 2023), SalaryType.SALARY.toString()),
                SalaryDb(1, 11000000, DateHelper.timestampOf(30, 5, 2023), SalaryType.SALARY.toString()),
                SalaryDb(1, 6000000, DateHelper.timestampOf(15, 6, 2023), SalaryType.SALARY.toString()),
                SalaryDb(1, 11000000, DateHelper.timestampOf(30, 6, 2023), SalaryType.SALARY.toString()),
                SalaryDb(1, 6000000, DateHelper.timestampOf(15, 7, 2023), SalaryType.SALARY.toString()),
                SalaryDb(1, 11000000, DateHelper.timestampOf(30, 7, 2023), SalaryType.SALARY.toString()),
                SalaryDb(1, 6000000, DateHelper.timestampOf(15, 8, 2023), SalaryType.SALARY.toString()),
                SalaryDb(1, 11000000, DateHelper.timestampOf(30, 8, 2023), SalaryType.SALARY.toString()),
            )
        )
        return SalaryMapper.toModelFlow(result)
    }

    suspend fun addSalary(model: SalaryModel): AppResult<Long> {
        Timber.d("addSalary($model)")
        return invoke {
            val mappedModel = SalaryMapper.toDb(model)
            salariesDao.insert(mappedModel)
        }
    }

    suspend fun updateSalary(model: SalaryModel): AppResult<Int> {
        Timber.d("updateSalary($model)")
        return invoke {
            val mappedModel = SalaryMapper.toDb(model)
            salariesDao.update(mappedModel)
        }
    }

    suspend fun deleteSalary(model: SalaryModel): AppResult<Int> {
        Timber.d("deleteSalary($model)")
        return invoke {
            val mappedModel = SalaryMapper.toDb(model)
            salariesDao.delete(mappedModel)
        }
    }
}
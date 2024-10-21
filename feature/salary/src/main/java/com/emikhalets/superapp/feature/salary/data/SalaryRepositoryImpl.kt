package com.emikhalets.superapp.feature.salary.data

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.invoke
import com.emikhalets.superapp.core.database.salary.table_salaries.SalariesDao
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import com.emikhalets.superapp.feature.salary.domain.SalaryRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class SalaryRepositoryImpl @Inject constructor(
    private val salariesDao: SalariesDao,
) : SalaryRepository {

    override fun getSalaries(): Flow<List<SalaryModel>> {
        Timber.d("getSalaries()")
        val result = salariesDao.getAllFlow()
        return SalaryMapper.toModelFlow(result)
    }

    override suspend fun addSalary(model: SalaryModel): AppResult<Boolean> {
        Timber.d("addSalary($model)")
        return invoke {
            val mappedModel = SalaryMapper.toDb(model)
            val id = salariesDao.insert(mappedModel)
            id > 0
        }
    }

    override suspend fun updateSalary(model: SalaryModel): AppResult<Boolean> {
        Timber.d("updateSalary($model)")
        return invoke {
            val mappedModel = SalaryMapper.toDb(model)
            val count = salariesDao.update(mappedModel)
            count > 0
        }
    }

    override suspend fun deleteSalary(model: SalaryModel): AppResult<Boolean> {
        Timber.d("deleteSalary($model)")
        return invoke {
            val mappedModel = SalaryMapper.toDb(model)
            val count = salariesDao.delete(mappedModel)
            count > 0
        }
    }
}
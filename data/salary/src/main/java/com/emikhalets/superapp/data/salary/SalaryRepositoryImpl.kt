package com.emikhalets.superapp.data.salary

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.invoke
import com.emikhalets.superapp.domain.salary.model.SalaryModel
import com.emikhalets.superapp.domain.salary.repository.SalaryRepository
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

    override suspend fun addSalary(model: SalaryModel): AppResult<Long> {
        Timber.d("addSalary($model)")
        return invoke {
            val mappedModel = SalaryMapper.toDb(model)
            salariesDao.insert(mappedModel)
        }
    }

    override suspend fun updateSalary(model: SalaryModel): AppResult<Int> {
        Timber.d("updateSalary($model)")
        return invoke {
            val mappedModel = SalaryMapper.toDb(model)
            salariesDao.update(mappedModel)
        }
    }

    override suspend fun deleteSalary(model: SalaryModel): AppResult<Int> {
        Timber.d("deleteSalary($model)")
        return invoke {
            val mappedModel = SalaryMapper.toDb(model)
            salariesDao.delete(mappedModel)
        }
    }

    override suspend fun deleteAllSalaries() {
        Timber.d("deleteAllSalaries()")
        return salariesDao.drop()
    }
}
package com.emikhalets.salary.data

import com.emikhalets.core.database.LocalResult
import com.emikhalets.core.database.invokeLocal
import com.emikhalets.core.database.salary.SalaryLocalDataSource
import com.emikhalets.salary.domain.model.SalaryModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: SalaryLocalDataSource,
) {

    fun getSalaries(): Flow<List<SalaryModel>> {
        Timber.d("getSalaries()")
        val result = localDataSource.getSalariesFlow()
        return SalaryMapper.toModelFlow(result)
    }

    suspend fun addSalary(model: SalaryModel): LocalResult<Long> {
        Timber.d("addSalary($model)")
        return invokeLocal {
            val mappedModel = SalaryMapper.toDb(model)
            localDataSource.insertSalary(mappedModel)
        }
    }

    suspend fun updateSalary(model: SalaryModel): LocalResult<Int> {
        Timber.d("updateSalary($model)")
        return invokeLocal {
            val mappedModel = SalaryMapper.toDb(model)
            localDataSource.updateSalary(mappedModel)
        }
    }

    suspend fun deleteSalary(model: SalaryModel): LocalResult<Int> {
        Timber.d("deleteSalary($model)")
        return invokeLocal {
            val mappedModel = SalaryMapper.toDb(model)
            localDataSource.deleteSalary(mappedModel)
        }
    }
}
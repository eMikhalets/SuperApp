package com.emikhalets.superapp.feature.salary.domain.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import com.emikhalets.superapp.feature.salary.domain.SalaryRepository
import javax.inject.Inject

class DeleteSalaryUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
) {

    suspend operator fun invoke(model: SalaryModel): AppResult<Boolean> {
        val deleteError = R.string.error_delete
        return when (val result = salaryRepository.deleteSalary(model)) {
            is AppResult.Failure -> AppResult.failure(StringValue.resource(deleteError))
            is AppResult.Success -> result
        }
    }
}
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

    suspend operator fun invoke(model: SalaryModel): Result {
        val deleteError = R.string.error_delete
        return when (salaryRepository.deleteSalary(model)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(deleteError))
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        data class Failure(val message: StringValue) : Result
        data object Success : Result
    }
}
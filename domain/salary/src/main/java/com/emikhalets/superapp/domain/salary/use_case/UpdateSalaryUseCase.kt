package com.emikhalets.superapp.domain.salary.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.domain.salary.SalaryModel
import com.emikhalets.superapp.domain.salary.SalaryRepository
import javax.inject.Inject

class UpdateSalaryUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
) {

    suspend operator fun invoke(model: SalaryModel): Result {
        val updateError = R.string.common_error_update
        return when (salaryRepository.updateSalary(model)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(updateError))
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        class Failure(val message: StringValue) : Result
        data object Success : Result
    }
}
package com.emikhalets.superapp.domain.salary.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.domain.salary.model.SalaryModel
import com.emikhalets.superapp.domain.salary.repository.SalaryRepository
import javax.inject.Inject

class InsertSalaryUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
) {

    suspend operator fun invoke(model: SalaryModel): Result {
        val insertError = R.string.common_error_insert
        return when (salaryRepository.addSalary(model)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(insertError))
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        class Failure(val message: StringValue) : Result
        data object Success : Result
    }
}
package com.emikhalets.superapp.domain.salary.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.domain.salary.model.SalaryModel
import com.emikhalets.superapp.domain.salary.repository.SalaryRepository
import javax.inject.Inject

class GetSalaryUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
) {

    suspend operator fun invoke(id: Long): Result {
        val getItemError = R.string.common_error_get_item
        return when (val result = salaryRepository.getSalary(id)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(getItemError))
            is AppResult.Success -> Result.Success(result.data)
        }
    }

    sealed interface Result {
        class Failure(val message: StringValue) : Result
        class Success(val model: SalaryModel) : Result
    }
}
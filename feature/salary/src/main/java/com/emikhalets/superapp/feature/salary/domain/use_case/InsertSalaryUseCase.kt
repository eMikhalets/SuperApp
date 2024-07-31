package com.emikhalets.superapp.feature.salary.domain.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.feature.salary.data.SalaryRepository
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import javax.inject.Inject

class InsertSalaryUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
) {

    suspend operator fun invoke(model: SalaryModel): AppResult<Long> {
        val insertError = R.string.common_error_insert
        return when (val result = salaryRepository.addSalary(model)) {
            is AppResult.Failure -> AppResult.failure(StringValue.resource(insertError))
            is AppResult.Success -> result
        }
    }
}
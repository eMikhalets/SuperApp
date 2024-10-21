package com.emikhalets.superapp.feature.salary.domain.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import com.emikhalets.superapp.feature.salary.domain.SalaryRepository
import javax.inject.Inject

class UpdateSalaryUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
) {

    suspend operator fun invoke(model: SalaryModel): AppResult<Boolean> {
        val updateError = R.string.error_update
        return when (val result = salaryRepository.updateSalary(model)) {
            is AppResult.Failure -> AppResult.failure(StringValue.resource(updateError))
            is AppResult.Success -> result
        }
    }
}
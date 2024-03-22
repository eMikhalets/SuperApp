package com.emikhalets.salary.domain.use_case

import com.emikhalets.core.database.LocalResult
import com.emikhalets.core.ui.StringValue
import com.emikhalets.salary.data.Repository
import com.emikhalets.salary.domain.model.SalaryModel
import javax.inject.Inject

class InsertSalaryUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(model: SalaryModel): Result {
        val insertError = com.emikhalets.core.R.string.core_error_insert
        return when (repository.addSalary(model)) {
            is LocalResult.Failure -> Result.Failure(StringValue.create(insertError))
            is LocalResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        class Failure(val message: StringValue) : Result
        data object Success : Result
    }
}
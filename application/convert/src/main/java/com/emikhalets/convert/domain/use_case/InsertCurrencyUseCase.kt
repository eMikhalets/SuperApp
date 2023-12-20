package com.emikhalets.convert.domain.use_case

import com.emikhalets.convert.data.Repository
import com.emikhalets.core.database.LocalResult
import com.emikhalets.core.database.getOrTrue
import com.emikhalets.core.ui.StringValue
import javax.inject.Inject

class InsertCurrencyUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(code: String): Result {
        val existResult = repository.isCodeExist(code).getOrTrue()
        if (existResult) return Result.Exist

        val insertError = com.emikhalets.core.R.string.core_error_insert
        return when (val result = repository.insertCode(code)) {
            is LocalResult.Failure -> Result.Failure(StringValue.create(insertError))
            is LocalResult.Success -> {
                if (result.data <= 0) {
                    Result.Failure(StringValue.create(insertError))
                } else {
                    checkExchanges(code)
                }
            }
        }
    }

    private suspend fun checkExchanges(code: String): Result {
        val updateError = com.emikhalets.core.R.string.core_error_update
        return when (repository.checkExchangesPostInsert(code)) {
            is LocalResult.Failure -> Result.Failure(StringValue.create(updateError))
            is LocalResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        class Failure(val message: StringValue) : Result
        data object Success : Result
        data object Exist : Result
    }
}
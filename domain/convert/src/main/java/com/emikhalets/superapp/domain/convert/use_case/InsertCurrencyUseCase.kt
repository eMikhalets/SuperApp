package com.emikhalets.superapp.domain.convert.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.getOrTrue
import com.emikhalets.superapp.domain.convert.ConvertRepository
import javax.inject.Inject

class InsertCurrencyUseCase @Inject constructor(
    private val convertRepository: ConvertRepository,
) {

    suspend operator fun invoke(code: String): Result {
        val existResult = convertRepository.isCodeExist(code).getOrTrue()
        if (existResult) return Result.Exist

        val insertError = R.string.common_error_insert
        return when (val result = convertRepository.insertCode(code)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(insertError))
            is AppResult.Success -> {
                if (result.data <= 0) {
                    Result.Failure(StringValue.resource(insertError))
                } else {
                    checkExchanges(code)
                }
            }
        }
    }

    private suspend fun checkExchanges(code: String): Result {
        val updateError = R.string.common_error_update
        return when (convertRepository.checkCurrencyPairsPostInsert(code)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(updateError))
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        class Failure(val message: StringValue) : Result
        data object Success : Result
        data object Exist : Result
    }
}
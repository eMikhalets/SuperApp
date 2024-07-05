package com.emikhalets.superapp.feature.convert.domain.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.feature.convert.data.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.CurrencyModel
import javax.inject.Inject

class DeleteCurrencyUseCase @Inject constructor(
    private val convertRepository: ConvertRepository,
) {

    suspend operator fun invoke(code: String): Result {
        val deleteError = R.string.common_error_delete
        when (convertRepository.deleteCurrency(code)) {
            is AppResult.Failure -> return Result.Failure(StringValue.resource(deleteError))
            is AppResult.Success -> Unit
        }
        when (convertRepository.deleteCurrencyPairs(code)) {
            is AppResult.Failure -> return Result.Failure(StringValue.resource(deleteError))
            is AppResult.Success -> Unit
        }

        val currencies: List<CurrencyModel>
        val getError = R.string.common_error_get_item
        when (val result = convertRepository.getCurrenciesSync()) {
            is AppResult.Failure -> return Result.Failure(StringValue.resource(getError))
            is AppResult.Success -> currencies = result.data
        }

        return if (currencies.count() == 1) {
            val lastCode = currencies.first().code
            when (convertRepository.insertFirstCurrencyPairs(lastCode)) {
                is AppResult.Failure -> Result.Failure(StringValue.internalError())
                is AppResult.Success -> Result.Success
            }
        } else {
            Result.Success
        }
    }

    sealed interface Result {
        class Failure(val message: StringValue) : Result
        data object Success : Result
    }
}
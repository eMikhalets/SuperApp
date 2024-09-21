package com.emikhalets.superapp.feature.convert.domain.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.CurrencyModel
import javax.inject.Inject

class DeleteCurrencyUseCase @Inject constructor(
    private val repository: ConvertRepository,
) {

    suspend operator fun invoke(code: String): Result {
        val deleteError = R.string.error_delete
        when (repository.deleteCurrency(code)) {
            is AppResult.Failure -> return Result.Failure(StringValue.resource(deleteError))
            is AppResult.Success -> Unit
        }
        when (repository.deleteCurrencyPairs(code)) {
            is AppResult.Failure -> return Result.Failure(StringValue.resource(deleteError))
            is AppResult.Success -> Unit
        }

        val currencies: List<CurrencyModel>
        val getError = R.string.error_get_item
        when (val result = repository.getCurrenciesSync()) {
            is AppResult.Failure -> return Result.Failure(StringValue.resource(getError))
            is AppResult.Success -> currencies = result.data
        }

        return if (currencies.count() == 1) {
            val lastCode = currencies.first().code
            when (repository.insertFirstCurrencyPairs(lastCode)) {
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
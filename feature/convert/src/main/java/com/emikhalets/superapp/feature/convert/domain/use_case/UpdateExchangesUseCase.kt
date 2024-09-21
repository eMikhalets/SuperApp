package com.emikhalets.superapp.feature.convert.domain.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.model.CurrencyValueModel
import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.CurrencyPairModel
import java.util.Date
import javax.inject.Inject

class UpdateExchangesUseCase @Inject constructor(
    private val repository: ConvertRepository,
) {

    suspend operator fun invoke(list: List<CurrencyPairModel>): Result {
        val needUpdateList = list.filter { it.isNeedUpdate() }
        val parseError = R.string.error_parsing
        return when (val result = repository.parseCurrencyPairs(needUpdateList)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(parseError))
            is AppResult.Success -> updateInDatabase(result.data, list)
        }
    }

    private suspend fun updateInDatabase(
        currencies: List<CurrencyValueModel>,
        exchanges: List<CurrencyPairModel>,
    ): Result {
        val updatedList = exchanges.map { item ->
            val currency = currencies.find { it.code == item.code }
            if (currency == null) {
                item.copy(value = 0.0, date = 0)
            } else {
                item.copy(value = currency.value, date = Date().time)
            }
        }
        val updateError = R.string.error_update
        return when (repository.updateCurrencyPairs(updatedList)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(updateError))
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        class Failure(val message: StringValue) : Result
        data object Success : Result
    }
}
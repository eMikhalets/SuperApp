package com.emikhalets.superapp.domain.convert.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.model.CurrencyValueModel
import com.emikhalets.superapp.domain.convert.CurrencyPairModel
import com.emikhalets.superapp.domain.convert.ConvertRepository
import java.util.Date
import javax.inject.Inject

class UpdateExchangesUseCase @Inject constructor(
    private val convertRepository: ConvertRepository,
) {

    suspend operator fun invoke(list: List<CurrencyPairModel>): Result {
        val needUpdateList = list.filter { it.isNeedUpdate() }
        val parseError = R.string.common_error_parsing
        return when (val result = convertRepository.parseCurrencyPairs(needUpdateList)) {
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
        val updateError = R.string.common_error_update
        return when (convertRepository.updateCurrencyPairs(updatedList)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(updateError))
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        class Failure(val message: StringValue) : Result
        data object Success : Result
    }
}
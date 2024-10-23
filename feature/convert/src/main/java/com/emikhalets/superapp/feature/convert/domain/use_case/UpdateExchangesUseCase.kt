package com.emikhalets.superapp.feature.convert.domain.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.timestamp
import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import javax.inject.Inject

class UpdateExchangesUseCase @Inject constructor(
    private val repository: ConvertRepository,
) {

    suspend operator fun invoke(list: List<ExchangeModel>): Result {
        val needUpdate = list.filter { it.isNeedUpdate() }
        if (needUpdate.isEmpty()) return Result.NotUpdated
        val fullCodes = needUpdate.map { it.fullCode }
        return when (val result = repository.loadRemoteExchanges(fullCodes)) {
            is AppResult.Failure -> Result.Error(StringValue.resource(R.string.error_parsing))
            is AppResult.Success -> updateInDatabase(result.data, needUpdate)
        }
    }

    private suspend fun updateInDatabase(
        currencies: List<Pair<String, Double>>,
        exchanges: List<ExchangeModel>,
    ): Result {
        if (currencies.isEmpty()) return Result.NotUpdated
        val updatedList = exchanges.map { item ->
            val currency = currencies.find { it.first == item.fullCode }
            if (currency == null) {
                item.copy(value = 0.0, updateDate = 0)
            } else {
                item.copy(value = currency.second, updateDate = timestamp())
            }
        }
        return when (val result = repository.updateExchanges(updatedList)) {
            is AppResult.Failure -> Result.Error(StringValue.resource(R.string.error_update))
            is AppResult.Success -> {
                if (result.data) {
                    Result.Success
                } else {
                    Result.NotUpdated
                }
            }
        }
    }

    sealed interface Result {
        data class Error(val value: StringValue) : Result
        data object NotUpdated : Result
        data object Success : Result
    }
}
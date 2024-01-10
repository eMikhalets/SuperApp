package com.emikhalets.convert.domain.use_case

import com.emikhalets.convert.R
import com.emikhalets.convert.data.Repository
import com.emikhalets.convert.domain.model.ExchangeModel
import com.emikhalets.core.database.LocalResult
import com.emikhalets.core.network.CurrencyPair
import com.emikhalets.core.network.RemoteResult
import com.emikhalets.core.ui.StringValue
import java.util.Date
import javax.inject.Inject

class UpdateExchangesUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(list: List<ExchangeModel>): Result {
        val needUpdateList = list.filter { it.isNeedUpdate() }
        val parseError = R.string.convert_parsing_error
        return when (val result = repository.parseExchanges(needUpdateList)) {
            is RemoteResult.Failure -> Result.Failure(StringValue.create(parseError))
            is RemoteResult.Success -> updateInDatabase(result.data, list)
            is RemoteResult.Empty -> Result.Empty
        }
    }

    private suspend fun updateInDatabase(
        currencies: List<CurrencyPair>,
        exchanges: List<ExchangeModel>,
    ): Result {
        val updatedList = exchanges.map { item ->
            val currency = currencies.find { it.code == item.fullCode }
            if (currency == null) {
                item.copy(value = 0.0, date = 0)
            } else {
                item.copy(value = currency.value, date = Date().time)
            }
        }
        val updateError = com.emikhalets.core.R.string.core_error_update
        return when (repository.updateExchanges(updatedList)) {
            is LocalResult.Failure -> Result.Failure(StringValue.create(updateError))
            is LocalResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        class Failure(val message: StringValue) : Result
        data object Success : Result
        data object Empty : Result
    }
}
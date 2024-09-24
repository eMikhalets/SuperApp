package com.emikhalets.superapp.feature.convert.domain.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import javax.inject.Inject

class DeleteCurrencyUseCase @Inject constructor(
    private val repository: ConvertRepository,
) {

    suspend operator fun invoke(code: String): Result {
        return when (val result = repository.getExchangesSync()) {
            is AppResult.Failure -> Result.Error(StringValue.resource(R.string.error_get_list))
            is AppResult.Success -> checkExchanges(code, result.data)
        }
    }

    private suspend fun checkExchanges(code: String, list: List<ExchangeModel>): Result {
        return if (list.size == 1) {
            val item = list.first()
            val newItem = if (item.mainCode == code) {
                item.copy(mainCode = item.subCode, subCode = "", value = 0.0, updateDate = 0)
            } else {
                item.copy(subCode = "", value = 0.0, updateDate = 0)
            }
            update(newItem)
        } else {
            delete(code)
        }
    }

    private suspend fun update(data: ExchangeModel): Result {
        return when (val result = repository.updateExchange(data)) {
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

    private suspend fun delete(code: String): Result {
        return when (val result = repository.deleteExchanges(code)) {
            is AppResult.Failure -> Result.Error(StringValue.resource(R.string.error_delete))
            is AppResult.Success -> {
                if (result.data) {
                    Result.Success
                } else {
                    Result.NotDeleted
                }
            }
        }
    }

    sealed interface Result {
        data class Error(val value: StringValue) : Result
        data object NotDeleted : Result
        data object NotUpdated : Result
        data object Success : Result
    }
}
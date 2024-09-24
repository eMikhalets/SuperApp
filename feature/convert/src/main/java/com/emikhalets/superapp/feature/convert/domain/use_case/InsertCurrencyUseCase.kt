package com.emikhalets.superapp.feature.convert.domain.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.feature.convert.R
import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import com.emikhalets.superapp.feature.convert.domain.buildCodesList
import javax.inject.Inject

class InsertCurrencyUseCase @Inject constructor(
    private val repository: ConvertRepository,
) {

    suspend operator fun invoke(code: String): Result {
        val validation = getValidateMessage(code)
        if (validation != null) return Result.Validation(validation)
        return checkExist(code)
    }

    private fun getValidateMessage(code: String): StringValue? {
        if (code.isEmpty()) {
            return StringValue.resource(R.string.convert_code_empty)
        }
        if (code.length != 3) {
            return StringValue.resource(R.string.convert_code_length_error)
        }
        if (code.all { it.isLetter() }) {
            return StringValue.resource(R.string.convert_code_letters_error)
        }
        return null
    }

    private suspend fun checkExist(code: String): Result {
        return when (val result = repository.isCodeExist(code)) {
            is AppResult.Failure -> Result.Error(StringValue.resource(R.string.error_item_exist))
            is AppResult.Success -> {
                if (result.data) {
                    return Result.Exist
                } else {
                    getExchanges(code)
                }
            }
        }
    }

    private suspend fun getExchanges(code: String): Result {
        return when (val result = repository.getExchangesSync()) {
            is AppResult.Failure -> Result.Error(StringValue.resource(R.string.error_get_list))
            is AppResult.Success -> prepareModel(code, result.data)
        }
    }

    private suspend fun prepareModel(code: String, list: List<ExchangeModel>): Result {
        return when {
            list.isEmpty() -> {
                insertExchange(ExchangeModel(code))
            }

            list.size == 1 && list.first().subCode.isBlank() -> {
                updateExchange(list.first().copy(subCode = code))
            }

            else -> {
                val exchanges = list.buildCodesList().map { ExchangeModel(it, code) }
                insertExchanges(exchanges)
            }
        }
    }

    private suspend fun insertExchange(data: ExchangeModel): Result {
        return when (repository.insertExchange(data)) {
            is AppResult.Failure -> Result.Error(StringValue.resource(R.string.error_insert))
            is AppResult.Success -> Result.Success
        }
    }

    private suspend fun insertExchanges(data: List<ExchangeModel>): Result {
        return when (repository.insertExchanges(data)) {
            is AppResult.Failure -> Result.Error(StringValue.resource(R.string.error_insert))
            is AppResult.Success -> Result.Success
        }
    }

    private suspend fun updateExchange(data: ExchangeModel): Result {
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

    sealed interface Result {
        data class Error(val value: StringValue) : Result
        data class Validation(val value: StringValue) : Result
        data object NotUpdated : Result
        data object Exist : Result
        data object Success : Result
    }
}
package com.emikhalets.convert.domain.use_case

import com.emikhalets.convert.data.Repository
import com.emikhalets.convert.domain.model.CurrencyModel
import com.emikhalets.core.database.LocalResult
import com.emikhalets.core.ui.StringValue
import javax.inject.Inject

class DeleteCurrencyUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(code: String): Result {
        val deleteError = com.emikhalets.core.R.string.core_error_delete
        when (repository.deleteCurrency(code)) {
            is LocalResult.Failure -> return Result.Failure(StringValue.create(deleteError))
            is LocalResult.Success -> Unit
        }
        when (repository.deleteExchanges(code)) {
            is LocalResult.Failure -> return Result.Failure(StringValue.create(deleteError))
            is LocalResult.Success -> Unit
        }

        val currencies: List<CurrencyModel>
        val getError = com.emikhalets.core.R.string.core_error_get
        when (val result = repository.getCurrenciesSync()) {
            is LocalResult.Failure -> return Result.Failure(StringValue.create(getError))
            is LocalResult.Success -> currencies = result.data
        }

        return if (currencies.count() == 1) {
            val lastCode = currencies.first().code
            when (repository.insertFirstExchange(lastCode)) {
                is LocalResult.Failure -> Result.Failure(StringValue.create())
                is LocalResult.Success -> Result.Success
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
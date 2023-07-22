package com.emikhalets.feature.currencies_convert.domain.use_case

import com.emikhalets.feature.currencies_convert.data.Repository
import com.emikhalets.feature.currencies_convert.domain.model.CurrencyModel
import javax.inject.Inject

class AddCurrencyUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(code: String) {
        return repository.insertCurrency(CurrencyModel(code))
    }
}
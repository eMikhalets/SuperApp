package com.emikhalets.feature.currencies_convert.domain.use_case

import com.emikhalets.feature.currencies_convert.data.Repository
import com.emikhalets.feature.currencies_convert.domain.model.CurrencyModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<List<CurrencyModel>> {
        return repository.getCurrencies()
    }
}
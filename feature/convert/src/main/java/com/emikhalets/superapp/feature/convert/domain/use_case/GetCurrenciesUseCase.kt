package com.emikhalets.superapp.feature.convert.domain.use_case

import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.CurrencyModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: ConvertRepository,
) {

    operator fun invoke(): Flow<List<CurrencyModel>> {
        return repository.getCurrencies()
    }
}
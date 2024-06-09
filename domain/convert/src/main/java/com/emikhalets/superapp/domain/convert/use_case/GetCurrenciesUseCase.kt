package com.emikhalets.superapp.domain.convert.use_case

import com.emikhalets.superapp.domain.convert.CurrencyModel
import com.emikhalets.superapp.domain.convert.ConvertRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val convertRepository: ConvertRepository,
) {

    operator fun invoke(): Flow<List<CurrencyModel>> {
        return convertRepository.getCurrencies()
    }
}
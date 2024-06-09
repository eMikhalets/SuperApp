package com.emikhalets.superapp.domain.convert.use_case

import com.emikhalets.superapp.domain.convert.CurrencyPairModel
import com.emikhalets.superapp.domain.convert.ConvertRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExchangesUseCase @Inject constructor(
    private val convertRepository: ConvertRepository,
) {

    operator fun invoke(): Flow<List<CurrencyPairModel>> {
        return convertRepository.getCurrencyPairs()
    }
}
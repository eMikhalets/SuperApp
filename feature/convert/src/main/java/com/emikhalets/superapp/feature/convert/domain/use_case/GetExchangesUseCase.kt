package com.emikhalets.superapp.feature.convert.domain.use_case

import com.emikhalets.superapp.feature.convert.data.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.CurrencyPairModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExchangesUseCase @Inject constructor(
    private val convertRepository: ConvertRepository,
) {

    operator fun invoke(): Flow<List<CurrencyPairModel>> {
        return convertRepository.getCurrencyPairs()
    }
}
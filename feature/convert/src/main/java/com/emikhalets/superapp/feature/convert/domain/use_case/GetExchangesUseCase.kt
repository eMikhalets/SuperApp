package com.emikhalets.superapp.feature.convert.domain.use_case

import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExchangesUseCase @Inject constructor(
    private val repository: ConvertRepository,
) {

    operator fun invoke(): Flow<List<ExchangeModel>> {
        return repository.getExchanges()
    }
}
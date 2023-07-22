package com.emikhalets.feature.currencies_convert.domain.use_case

import com.emikhalets.feature.currencies_convert.data.Repository
import com.emikhalets.feature.currencies_convert.domain.model.ExchangeModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetExchangesUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<List<ExchangeModel>> {
        return repository.getExchanges()
    }
}
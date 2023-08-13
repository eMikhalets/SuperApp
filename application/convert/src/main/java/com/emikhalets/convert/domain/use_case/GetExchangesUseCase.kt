package com.emikhalets.convert.domain.use_case

import com.emikhalets.convert.data.Repository
import com.emikhalets.convert.domain.model.ExchangeModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetExchangesUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<List<ExchangeModel>> {
        return repository.getExchanges()
    }
}
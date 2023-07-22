package com.emikhalets.feature.currencies_convert.domain.use_case

import com.emikhalets.feature.currencies_convert.data.Repository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetExchangesDateUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<Long> {
        return repository.getExchangesDate()
    }
}
package com.emikhalets.convert.domain.usecase

import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.convert.domain.repository.ConvertRepository
import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import javax.inject.Inject

class GetExchangesUseCase @Inject constructor(
    private val convertRepository: ConvertRepository,
) {

    suspend operator fun invoke(): AppResult<List<ExchangeEntity>> {
        logi("GetExchangesUC", "Invoke")
        return convertRepository.getExchanges()
    }
}
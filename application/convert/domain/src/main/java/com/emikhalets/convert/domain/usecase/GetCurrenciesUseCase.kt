package com.emikhalets.convert.domain.usecase

import com.emikhalets.convert.domain.entity.CurrencyEntity
import com.emikhalets.convert.domain.repository.ConvertRepository
import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCurrenciesUseCase @Inject constructor(
    private val convertRepository: ConvertRepository,
) {

    suspend operator fun invoke(): AppResult<Flow<List<CurrencyEntity>>> {
        logi("GetCurrenciesUC", "Invoke")
        return convertRepository.getCurrencies()
    }
}
package com.emikhalets.convert.domain.usecase

import com.emikhalets.convert.domain.repository.ConvertRepository
import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val convertRepository: ConvertRepository,
) {

    suspend operator fun invoke(value: Double): AppResult<Map<String, Double>> {
        logi("ConvertCurrencyUC", "Invoke")
        return AppResult.success(emptyMap())
    }
}
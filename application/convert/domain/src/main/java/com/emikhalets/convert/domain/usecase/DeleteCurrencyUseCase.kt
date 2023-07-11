package com.emikhalets.convert.domain.usecase

import com.emikhalets.convert.domain.repository.ConvertRepository
import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.logi
import javax.inject.Inject

class DeleteCurrencyUseCase @Inject constructor(
    private val convertRepository: ConvertRepository,
) {

    suspend operator fun invoke(code: String): AppResult<Unit> {
        logi("DeleteCurrencyUC", "Invoke")
        return convertRepository.deleteCurrency(code)
    }
}
package com.emikhalets.convert.domain.usecase

import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.convert.domain.entity.getCurrencies
import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.execute
import com.emikhalets.core.common.logi
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConvertCurrencyUseCase @Inject constructor() {

    suspend operator fun invoke(
        input: List<ExchangeEntity>,
        base: String,
        value: Double,
    ): AppResult<Map<String, Double>> {
        logi("ConvertCurrencyUC", "Invoke")
        return withContext(Dispatchers.IO) {
            execute {
                input.getCurrencies()
                    .associateBy(
                        { it },
                        {
                            if (it == base) {
                                value
                            } else {
                                val exchangeValue = input.find { item -> item.isCurrency(base, it) }
                                exchangeValue?.let { item -> item.value * value } ?: 0.0
                            }
                        }
                    )
            }
        }
    }
}
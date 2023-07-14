package com.emikhalets.convert.domain.usecase

import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.execute
import com.emikhalets.core.common.logi
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConvertCurrencyUseCase @Inject constructor() {

    suspend operator fun invoke(
        currencies: List<Pair<String, Double>>,
        exchanges: List<ExchangeEntity>,
        baseCurrency: String,
        baseValue: Double?,
    ): AppResult<List<Pair<String, Double>>> {
        logi("ConvertCurrencyUC", "Invoke")
        return withContext(Dispatchers.IO) {
            val default = baseValue ?: 0.0
            execute {
                currencies.map { pair ->
                    if (pair.first != baseCurrency) {
                        exchanges
                            .find { it.containsPair(pair.first, baseCurrency) }
                            ?.let { Pair(pair.first, it.calculate(baseCurrency, default)) }
                            ?: pair
                    } else {
                        pair
                    }
                }
            }
        }
    }
}
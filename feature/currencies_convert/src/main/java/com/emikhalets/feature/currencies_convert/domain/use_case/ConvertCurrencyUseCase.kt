package com.emikhalets.feature.currencies_convert.domain.use_case

import com.emikhalets.feature.currencies_convert.domain.model.ExchangeModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConvertCurrencyUseCase @Inject constructor() {

    suspend operator fun invoke(
        currencies: List<Pair<String, Long>>,
        exchanges: List<ExchangeModel>,
        baseCurrency: String,
        baseValue: Long,
    ): List<Pair<String, Long>> {
        return withContext(Dispatchers.IO) {
            currencies.map { pair ->
                if (pair.first != baseCurrency) {
                    exchanges.find { it.containsPair(pair.first, baseCurrency) }
                        ?.let { Pair(pair.first, it.calculate(baseCurrency, baseValue)) }
                        ?: Pair(pair.first, 0)
                } else {
                    pair
                }
            }
        }
    }
}
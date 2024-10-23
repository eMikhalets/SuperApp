package com.emikhalets.superapp.feature.convert.domain.use_case

import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor() {

    suspend operator fun invoke(
        pairs: List<Pair<String, Long>>,
        exchanges: List<ExchangeModel>,
        baseCode: String,
        baseValue: Long,
    ): List<Pair<String, Long>> {
        return withContext(Dispatchers.IO) {
            pairs.map { pair ->
                val code = pair.first
                if (code != baseCode) {
                    exchanges.find { it.contains(code, baseCode) }
                        ?.let { Pair(code, it.calculate(baseCode, baseValue)) }
                        ?: Pair(code, 0)
                } else {
                    Pair(code, baseValue)
                }
            }
        }
    }

    private fun ExchangeModel.contains(base: String, currency: String): Boolean {
        return (mainCode == base && subCode == currency) ||
                (mainCode == currency && subCode == base)
    }

    private fun ExchangeModel.calculate(code: String, value: Long): Long {
        return when (code) {
            mainCode -> (value * this.value).toLong()
            subCode -> (value * (1 / this.value)).toLong()
            else -> 0
        }
    }
}
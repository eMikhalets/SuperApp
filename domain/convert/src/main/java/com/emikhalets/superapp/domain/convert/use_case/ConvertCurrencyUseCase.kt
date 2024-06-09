package com.emikhalets.superapp.domain.convert.use_case

import com.emikhalets.superapp.domain.convert.CurrencyPairModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor() {

    suspend operator fun invoke(
        pairs: List<Pair<String, String>>,
        exchanges: List<CurrencyPairModel>,
        baseCode: String,
        baseValue: String,
    ): List<Pair<String, String>> {
        return withContext(Dispatchers.IO) {
            val value = baseValue.format()
            pairs.map { pair ->
                if (pair.first != baseCode) {
                    exchanges.find { it.containsPair(pair.first, baseCode) }
                        ?.let { Pair(pair.first, it.calculateAndFormat(baseCode, value)) }
                        ?: Pair(pair.first, "")
                } else {
                    Pair(pair.first, baseValue)
                }
            }
        }
    }

    private fun CurrencyPairModel.calculateAndFormat(baseCode: String, value: Long): String {
        return calculate(baseCode, value).toCurrencyValue()
    }

    private fun Long.toCurrencyValue(): String {
        if (this < 10) return "0.0$this"
        if (this < 100) return "0.$this"
        val leftPart = (this / 100).toString()
        val rightPart = (this - ((this / 100) * 100)).toString()
        return "$leftPart.$rightPart".trim()
    }

    private fun String.format(): Long {
        val parts = this.split(".")
        val leftPart = (parts.getOrNull(0)?.toLongOrNull() ?: 0) * 100
        val rightPart = parts.getOrNull(1)?.toLongOrNull() ?: 0
        return leftPart + rightPart
    }
}
package com.emikhalets.convert.data

import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.core.common.loge
import com.emikhalets.core.common.logi
import com.emikhalets.core.network.AppParser
import org.jsoup.nodes.Element

class CurrencyParser : AppParser() {

    private val source = "https://tradingeconomics.com/currencies?base="
    private val currencyRowKey = "[data-symbol*=CUR]"
    private val dataSymbolKey = "data-symbol"

    suspend fun replaceExchangesValues(
        input: List<ExchangeEntity>,
        date: Long,
    ): List<ExchangeEntity> {
        logi(TAG, "Get exchanges: input = $input")
        val codes = input.map { it.code }
        val newValues = input
            .filter { it.isOldValue() }
            .map { it.code.take(3) }.toSet()
            .map { parseSource("$source$it") }
            .flatMap { it.getElements(currencyRowKey) }
            .filter { codes.containsDataSymbol(it) }
            .mapNotNull { convertData(it) }
            .associateBy({ it.first }, { it.second })
        return input.map { entity ->
            val value = newValues[entity.code]
            value?.let { entity.withValue(it, date) } ?: entity
        }
    }

    private fun convertData(element: Element): Pair<String, Double?>? {
        return try {
            val data = element.text().split(" ")
            Pair(data[0], data[1].toDoubleOrNull())
        } catch (e: IndexOutOfBoundsException) {
            loge(TAG, e)
            null
        }
    }

    private fun List<String>.containsDataSymbol(element: Element): Boolean {
        return try {
            val code = element.attr(dataSymbolKey).split(":").first()
            contains(code)
        } catch (e: IndexOutOfBoundsException) {
            loge(TAG, e)
            false
        }
    }

    companion object {

        private const val TAG = "CurrencyParser"
    }
}


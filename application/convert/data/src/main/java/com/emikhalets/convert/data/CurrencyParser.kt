package com.emikhalets.convert.data

import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.core.common.loge
import com.emikhalets.core.common.logi
import com.emikhalets.core.network.AppParser
import org.jsoup.nodes.Element

class CurrencyParser : AppParser() {

    private val source = "https://tradingeconomics.com/currencies?base="
    private val currencyRowKey = "[data-symbol*=CUR]"

    suspend fun getExchanges(input: MutableList<ExchangeEntity>) {
        logi(TAG, "Get exchanges: input = $input")
        if (input.count() == 1 && input.first().secondaryCurrency.isBlank()) return
        val codes = input.map { it.getRequestCode() }.toSet()
        val valuesMap = codes
            .mapNotNull { it.take(3).ifEmpty { null } }
            .toSet()
            .map { parseSource("$source$it") }
            .flatMap { it.getElements(currencyRowKey) }
            .mapNotNull { convertData(it) }
            .filter { codes.contains(it.first) }
            .associateBy({ it.first }, { it.second })
        input.forEachIndexed { index, entity ->
            val value = valuesMap[entity.getRequestCode()]
            value?.let { input[index] = entity.withValue(it) }
        }
    }

    private fun convertData(element: Element): Pair<String, Double?>? {
        return try {
            val data = element.data().split(" ")
            Pair(data[0], data[1].toDoubleOrNull())
        } catch (e: IndexOutOfBoundsException) {
            loge(TAG, e)
            null
        }
    }

    companion object {

        private const val TAG = "CurrencyParser"
    }
}


package com.emikhalets.core.network

import com.emikhalets.core.common.loge
import javax.inject.Inject
import org.jsoup.nodes.Element

class CurrencyParser @Inject constructor() : AppParser() {

    private val source = "https://tradingeconomics.com/currencies?base="
    private val currencyRowKey = "[data-symbol*=CUR]"
    private val dataSymbolKey = "data-symbol"

    suspend fun loadExchangesValues(codes: List<String>): List<Pair<String, Double>> {
        return codes
            .map { it.take(3) }.toSet()
            .map { parseSource("$source$it") }
            .flatMap { it.getElements(currencyRowKey) }
            .filter { codes.containsDataSymbol(it) }
            .mapNotNull { convertData(it) }
            .associateBy({ it.first }, { it.second })
            .toList()
    }

    private fun convertData(element: Element): Pair<String, Double>? {
        return try {
            val data = element.text().split(" ")
            val code = data[0]
            val value = data[1].toDoubleOrNull()
            checkNotNull(value)
            Pair(code, value)
        } catch (e: IndexOutOfBoundsException) {
            loge(TAG, e)
            null
        } catch (e: IllegalStateException) {
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


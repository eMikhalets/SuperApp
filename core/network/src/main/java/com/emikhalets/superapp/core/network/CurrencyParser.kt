package com.emikhalets.superapp.core.network

import org.jsoup.nodes.Element
import timber.log.Timber
import javax.inject.Inject

class CurrencyParser @Inject constructor() : AppParser {

    private val source = "https://tradingeconomics.com/currencies?base="
    private val currencyRowKey = "[data-symbol*=CUR]"
    private val dataSymbolKey = "data-symbol"

    suspend fun parseCurrencyPairs(codes: List<String>): List<CurrencyValueModel> {
        return codes
            .map { it.take(3) }.toSet()
            .map { parseSource("$source$it") }
            .flatMap { it.getElements(currencyRowKey) }
            .filter { codes.containsDataSymbol(it) }
            .mapNotNull { convertData(it) }
    }

    private fun convertData(element: Element): CurrencyValueModel? {
        return try {
            val data = element.text().split(" ")
            val code = data[0]
            val value = data[1].toDouble()
            CurrencyValueModel(code, value)
        } catch (e: IndexOutOfBoundsException) {
            Timber.e(e)
            null
        } catch (e: IllegalStateException) {
            Timber.e(e)
            null
        }
    }

    private fun List<String>.containsDataSymbol(element: Element): Boolean {
        return try {
            val code = element.attr(dataSymbolKey).split(":").first()
            contains(code)
        } catch (e: IndexOutOfBoundsException) {
            Timber.e(e)
            false
        }
    }
}

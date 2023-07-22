package com.emikhalets.core.network

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CurrencyRemoteDataSource @Inject constructor(
    private val currencyParser: CurrencyParser,
) {

    fun parseCurrencies(codes: List<String>): Flow<List<Pair<String, Double>>> {
        return currencyParser.replaceExchangesValues()
    }
}
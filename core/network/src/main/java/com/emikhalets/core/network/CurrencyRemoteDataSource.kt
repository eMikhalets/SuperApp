package com.emikhalets.core.network

import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor(
    private val currencyParser: CurrencyParser,
) {

    suspend fun parseCurrencies(codes: List<String>): List<Pair<String, Double>> {
        return currencyParser.loadExchangesValues(codes)
    }
}
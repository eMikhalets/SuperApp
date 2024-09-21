package com.emikhalets.superapp.feature.convert.domain

import com.emikhalets.superapp.core.common.AppResult
import kotlinx.coroutines.flow.Flow

interface ConvertRepository {

    fun getExchanges(): Flow<List<ExchangeModel>>

    suspend fun getExchangesSync(): AppResult<List<ExchangeModel>>

    suspend fun updateExchanges(data: List<ExchangeModel>): AppResult<Boolean>

    suspend fun updateExchange(data: ExchangeModel): AppResult<Boolean>

    suspend fun insertExchange(data: ExchangeModel): AppResult<Boolean>

    suspend fun insertExchanges(data: List<ExchangeModel>): AppResult<Boolean>

    suspend fun deleteExchanges(data: List<ExchangeModel>): AppResult<Boolean>

    suspend fun loadRemoteExchanges(data: List<String>): AppResult<List<Pair<String, Double>>>


    suspend fun insertFirstCurrencyPairs(code: String): AppResult<Unit>

    suspend fun checkCurrencyPairsPostInsert(code: String): AppResult<Unit>

    fun getCurrencies(): Flow<List<ExchangeModel>>

    suspend fun deleteCurrency(code: String): AppResult<Unit>
}
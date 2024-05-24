package com.emikhalets.superapp.domain.convert.repository

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.model.CurrencyValueModel
import com.emikhalets.superapp.domain.convert.model.CurrencyModel
import com.emikhalets.superapp.domain.convert.model.CurrencyPairModel
import kotlinx.coroutines.flow.Flow

interface ConvertRepository {

    // Exchanges

    fun getCurrencyPairs(): Flow<List<CurrencyPairModel>>

    suspend fun updateCurrencyPairs(list: List<CurrencyPairModel>): AppResult<Int>

    suspend fun deleteCurrencyPairs(code: String): AppResult<Unit>

    suspend fun insertFirstCurrencyPairs(code: String): AppResult<Unit>

    suspend fun parseCurrencyPairs(list: List<CurrencyPairModel>): AppResult<List<CurrencyValueModel>>

    suspend fun checkCurrencyPairsPostInsert(code: String): AppResult<Unit>

    // Currencies

    fun getCurrencies(): Flow<List<CurrencyModel>>

    suspend fun getCurrenciesSync(): AppResult<List<CurrencyModel>>

    suspend fun isCodeExist(code: String): AppResult<Boolean>

    suspend fun insertCode(code: String): AppResult<Long>

    suspend fun deleteCurrency(code: String): AppResult<Unit>
}
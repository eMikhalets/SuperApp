package com.emikhalets.superapp.feature.convert.domain

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.model.CurrencyValueModel
import kotlinx.coroutines.flow.Flow

interface ConvertRepository {

    fun getCurrencyPairs(): Flow<List<CurrencyPairModel>>

    suspend fun updateCurrencyPairs(list: List<CurrencyPairModel>): AppResult<Int>

    suspend fun deleteCurrencyPairs(code: String): AppResult<Unit>

    suspend fun insertFirstCurrencyPairs(code: String): AppResult<Unit>

    suspend fun parseCurrencyPairs(
        list: List<CurrencyPairModel>,
    ): AppResult<List<CurrencyValueModel>>

    suspend fun checkCurrencyPairsPostInsert(code: String): AppResult<Unit>

    fun getCurrencies(): Flow<List<CurrencyModel>>

    suspend fun getCurrenciesSync(): AppResult<List<CurrencyModel>>

    suspend fun isCodeExist(code: String): AppResult<Boolean>

    suspend fun insertCode(code: String): AppResult<Long>

    suspend fun deleteCurrency(code: String): AppResult<Unit>
}
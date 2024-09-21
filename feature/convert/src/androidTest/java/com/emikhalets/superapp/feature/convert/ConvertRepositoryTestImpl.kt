package com.emikhalets.superapp.feature.convert

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.model.CurrencyValueModel
import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.CurrencyModel
import com.emikhalets.superapp.feature.convert.domain.CurrencyPairModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.lastOrNull

class ConvertRepositoryTestImpl : ConvertRepository {

    var currencyPairsFlow = MutableSharedFlow<List<CurrencyPairModel>>()
    var currenciesFlow = MutableSharedFlow<List<CurrencyModel>>()

    // CurrencyPairs

    override fun getCurrencyPairs(): Flow<List<CurrencyPairModel>> {
        return currencyPairsFlow
    }

    override suspend fun updateCurrencyPairs(list: List<CurrencyPairModel>): AppResult<Int> {
        return TODO()
    }

    override suspend fun deleteCurrencyPairs(code: String): AppResult<Unit> {
        return TODO()
    }

    override suspend fun insertFirstCurrencyPairs(code: String): AppResult<Unit> {
        return TODO()
    }

    override suspend fun parseCurrencyPairs(
        list: List<CurrencyPairModel>,
    ): AppResult<List<CurrencyValueModel>> {
        return TODO()
    }

    override suspend fun checkCurrencyPairsPostInsert(code: String): AppResult<Unit> {
        return TODO()
    }

    // Currencies

    override fun getCurrencies(): Flow<List<CurrencyModel>> {
        return currenciesFlow
    }

    override suspend fun getCurrenciesSync(): AppResult<List<CurrencyModel>> {
        return TODO()
    }

    override suspend fun isCodeExist(code: String): AppResult<Boolean> {
        return AppResult.success(currenciesFlow.lastOrNull()?.find { it.code == code } != null)
    }

    override suspend fun insertCode(code: String): AppResult<Long> {
        return AppResult.success(currenciesFlow.tryEmit(code))
    }

    override suspend fun deleteCurrency(code: String): AppResult<Unit> {
        return TODO()
    }
}
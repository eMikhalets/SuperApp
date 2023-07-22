package com.emikhalets.feature.currencies_convert.data

import com.emikhalets.feature.currencies_convert.domain.model.CurrencyModel
import com.emikhalets.feature.currencies_convert.domain.model.ExchangeModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository @Inject constructor(
//    private val localDataSource: LocalNotesDataSource,
) {

    suspend fun parseExchanges(currencies: List<String>) {
//        localDataSource.insertNote(model.toDb())
    }

    suspend fun insertCurrency(model: CurrencyModel) {
//        localDataSource.insertNote(model.toDb())
    }

    suspend fun deleteCurrency(model: CurrencyModel) {
    }

    suspend fun insertExchange(model: ExchangeModel) {
//        localDataSource.insertNote(model.toDb())
    }

    suspend fun updateExchange(model: ExchangeModel) {
//        localDataSource.insertNote(model.toDb())
    }

    suspend fun deleteExchange(model: ExchangeModel) {
    }

    fun getExchanges(): Flow<List<ExchangeModel>> {
        return flowOf()
    }

    fun getCurrencies(): Flow<List<CurrencyModel>> {
        return flowOf()
    }
}
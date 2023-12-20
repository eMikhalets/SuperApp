package com.emikhalets.core.database.convert

import com.emikhalets.core.database.convert.table_currencies.CurrenciesDao
import com.emikhalets.core.database.convert.table_currencies.CurrencyDb
import com.emikhalets.core.database.convert.table_exchanges.ExchangeDb
import com.emikhalets.core.database.convert.table_exchanges.ExchangesDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ConvertLocalDataSource @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    private val exchangesDao: ExchangesDao,
) {

    fun getCurrenciesFlow(): Flow<List<CurrencyDb>> {
        return currenciesDao.getAllFlow()
    }

    fun getExchangesFlow(): Flow<List<ExchangeDb>> {
        return exchangesDao.getAllFlow()
    }

    suspend fun updateExchanges(list: List<ExchangeDb>): Int {
        return exchangesDao.update(list)
    }

    suspend fun isCurrencyExist(code: String): Boolean {
        return currenciesDao.isCodeExist(code)
    }

    suspend fun insertCurrency(model: CurrencyDb): Long {
        return currenciesDao.insert(model)
    }

    suspend fun insertExchange(model: ExchangeDb): Long {
        return exchangesDao.insert(model)
    }

    suspend fun updateExchange(model: ExchangeDb) {
        exchangesDao.update(model)
    }

    suspend fun insertExchanges(list: List<ExchangeDb>): List<Long> {
        return exchangesDao.insert(list)
    }

    suspend fun getCurrencies(): List<CurrencyDb> {
        return currenciesDao.getAll()
    }

    suspend fun getExchanges(): List<ExchangeDb> {
        return exchangesDao.getAll()
    }

    suspend fun deleteCurrency(code: String) {
        return currenciesDao.deleteByCode(code)
    }

    suspend fun deleteExchanges(code: String) {
        return exchangesDao.deleteByCode(code)
    }

    suspend fun dropExchanges() {
        return exchangesDao.drop()
    }
}
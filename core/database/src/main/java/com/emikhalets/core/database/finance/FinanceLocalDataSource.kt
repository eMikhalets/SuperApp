package com.emikhalets.core.database.finance

import com.emikhalets.core.database.finance.table_convert_currencies.ConvertCurrenciesDao
import com.emikhalets.core.database.finance.table_convert_currencies.ConvertCurrencyDb
import com.emikhalets.core.database.finance.table_currencies.CurrenciesDao
import com.emikhalets.core.database.finance.table_exchanges.ExchangeDb
import com.emikhalets.core.database.finance.table_exchanges.ExchangesDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FinanceLocalDataSource @Inject constructor(
    private val convertCurrenciesDao: ConvertCurrenciesDao,
    private val currenciesDao: CurrenciesDao,
    private val exchangesDao: ExchangesDao,
) {

    fun getCurrencies(): Flow<List<ConvertCurrencyDb>> {
        return convertCurrenciesDao.getAllFlow()
    }

    fun getExchanges(): Flow<List<ExchangeDb>> {
        return exchangesDao.getAllFlow()
    }

    suspend fun updateExchanges(list: List<ExchangeDb>) {
        return exchangesDao.update(list)
    }

    suspend fun isCurrencyExist(code: String): Boolean {
        return convertCurrenciesDao.isCodeExist(code)
    }

    suspend fun insertCurrency(model: ConvertCurrencyDb) {
        convertCurrenciesDao.insert(model)
    }

    suspend fun insertExchange(model: ExchangeDb) {
        exchangesDao.insert(model)
    }

    suspend fun insertExchanges(list: List<ExchangeDb>) {
        exchangesDao.insert(list)
    }

    suspend fun updateExchange(model: ExchangeDb) {
        exchangesDao.update(model)
    }

    suspend fun getCurrenciesSync(): List<ConvertCurrencyDb> {
        return convertCurrenciesDao.getAll()
    }

    suspend fun getExchangesSync(): List<ExchangeDb> {
        return exchangesDao.getAll()
    }

    suspend fun deleteCurrency(code: String) {
        convertCurrenciesDao.deleteByCode(code)
    }

    suspend fun deleteExchanges(code: String) {
        exchangesDao.deleteByCode(code)
    }

    suspend fun dropExchanges() {
        exchangesDao.drop()
    }
}
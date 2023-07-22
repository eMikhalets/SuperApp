package com.emikhalets.core.database.finance

import com.emikhalets.core.database.finance.table_currencies.CurrenciesDao
import com.emikhalets.core.database.finance.table_currencies.CurrencyDb
import com.emikhalets.core.database.finance.table_exchanges.ExchangeDb
import com.emikhalets.core.database.finance.table_exchanges.ExchangesDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FinanceLocalDataSource @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    private val exchangesDao: ExchangesDao,
) {

    fun getCurrencies(): Flow<List<CurrencyDb>> {
        return currenciesDao.getAllFlow()
    }

    fun getExchanges(): Flow<List<ExchangeDb>> {
        return exchangesDao.getAllFlow()
    }

    suspend fun updateExchanges(list: List<ExchangeDb>) {
        return exchangesDao.update(list)
    }
}
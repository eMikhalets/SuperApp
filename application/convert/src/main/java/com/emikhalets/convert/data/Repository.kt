package com.emikhalets.convert.data

import com.emikhalets.convert.domain.model.CurrencyModel
import com.emikhalets.convert.domain.model.CurrencyModel.Companion.toDb
import com.emikhalets.convert.domain.model.CurrencyModel.Companion.toModelFlow
import com.emikhalets.convert.domain.model.ExchangeModel
import com.emikhalets.convert.domain.model.ExchangeModel.Companion.toDb
import com.emikhalets.convert.domain.model.ExchangeModel.Companion.toDbList
import com.emikhalets.convert.domain.model.ExchangeModel.Companion.toModelFlow
import com.emikhalets.convert.domain.model.filterNeedUpdate
import com.emikhalets.core.database.convert.CurrenciesLocalDataSource
import com.emikhalets.core.database.convert.table_currencies.CurrencyDb
import com.emikhalets.core.database.convert.table_exchanges.ExchangeDb
import com.emikhalets.core.datastore.convert.FinancePrefsDataSource
import com.emikhalets.core.network.CurrencyRemoteDataSource
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class Repository @Inject constructor(
    private val localDataSource: CurrenciesLocalDataSource,
    private val remoteDataSource: CurrencyRemoteDataSource,
    private val financePrefsSource: FinancePrefsDataSource,
) {

    fun getExchangesDate(): Flow<Long> {
        return financePrefsSource.getExchangesDate()
    }

    fun getExchanges(): Flow<List<ExchangeModel>> {
        return localDataSource.getExchanges()
            .toModelFlow()
            .onEach { invokeUpdatingExchanges(it) }
    }

    private suspend fun invokeUpdatingExchanges(list: List<ExchangeModel>) {
        val date = Date().time
        val updatedValues = list.filterNeedUpdate()
            .parseCurrencies()
            .mapIndexed { index, item -> list[index].withValue(item.second, date) }
        localDataSource.updateExchanges(updatedValues.toDbList())
        financePrefsSource.setExchangesDate(date)
    }

    private suspend fun List<ExchangeModel>.parseCurrencies(): List<Pair<String, Double>> {
        return remoteDataSource.parseCurrencies(this.map { it.code })
    }

    fun getCurrencies(): Flow<List<CurrencyModel>> {
        return localDataSource.getCurrencies().toModelFlow()
    }

    suspend fun insertCurrency(code: String) {
        if (!localDataSource.isCurrencyExist(code)) {
            val currency = CurrencyModel(code).toDb()
            localDataSource.insertCurrency(currency)
            val currencies = localDataSource.getCurrenciesSync()
            val exchanges = localDataSource.getExchangesSync()
            when (currencies.count()) {
                0 -> Unit
                1 -> localDataSource.insertExchange(ExchangeModel(code).toDb())
                2 -> localDataSource.updateExchange(exchanges.getFirstWithNewCode(code))
                else -> localDataSource.insertExchanges(currencies.createNewExchanges(code))
            }
        }
    }

    private fun List<ExchangeDb>.getFirstWithNewCode(code: String): ExchangeDb {
        return first().copy(code = "${first().code}$code")
    }

    private fun List<CurrencyDb>.createNewExchanges(code: String): List<ExchangeDb> {
        return dropLast(1).map { ExchangeModel(it.code, code).toDb() }
    }

    suspend fun deleteCurrency(code: String) {
        localDataSource.deleteCurrency(code)
        localDataSource.deleteExchanges(code)
        val currencies = localDataSource.getCurrenciesSync()
        if (currencies.count() == 1) {
            val lastCode = currencies.first().code
            localDataSource.dropExchanges()
            localDataSource.insertExchange(ExchangeModel(lastCode).toDb())
        }
    }
}
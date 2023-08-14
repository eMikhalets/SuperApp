package com.emikhalets.convert.data

import com.emikhalets.convert.domain.model.CurrencyModel
import com.emikhalets.convert.domain.model.CurrencyModel.Companion.toDb
import com.emikhalets.convert.domain.model.CurrencyModel.Companion.toModelFlow
import com.emikhalets.convert.domain.model.ExchangeModel
import com.emikhalets.convert.domain.model.ExchangeModel.Companion.toDb
import com.emikhalets.convert.domain.model.ExchangeModel.Companion.toDbList
import com.emikhalets.convert.domain.model.ExchangeModel.Companion.toModelFlow
import com.emikhalets.core.common.extensions.logd
import com.emikhalets.core.database.convert.CurrenciesLocalDataSource
import com.emikhalets.core.database.convert.table_currencies.CurrencyDb
import com.emikhalets.core.database.convert.table_exchanges.ExchangeDb
import com.emikhalets.core.network.CurrencyRemoteDataSource
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class Repository @Inject constructor(
    private val localDataSource: CurrenciesLocalDataSource,
    private val remoteDataSource: CurrencyRemoteDataSource,
) {

    fun getExchanges(): Flow<List<ExchangeModel>> {
        logd("getExchanges()")
        return localDataSource.getExchanges().toModelFlow()
    }

    fun getCurrencies(): Flow<List<CurrencyModel>> {
        logd("getCurrencies()")
        return localDataSource.getCurrencies().toModelFlow()
    }

    suspend fun updateExchanges(list: List<ExchangeModel>) {
        logd("updateExchanges(): $list")
        val date = Date().time
        val updatedValues = list
            .filter { it.isNeedUpdate() }
            .parseCurrencies()
            .mapIndexed { index, item -> list[index].withValue(item.second, date) }
        localDataSource.updateExchanges(updatedValues.toDbList())
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

    private suspend fun List<ExchangeModel>.parseCurrencies(): List<Pair<String, Double>> {
        return remoteDataSource.parseCurrencies(this.map { it.code })
    }

    private fun List<ExchangeDb>.getFirstWithNewCode(code: String): ExchangeDb {
        return first().copy(code = "${first().code}$code")
    }

    private fun List<CurrencyDb>.createNewExchanges(code: String): List<ExchangeDb> {
        return dropLast(1).map { ExchangeModel(it.code, code).toDb() }
    }
}
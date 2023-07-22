package com.emikhalets.feature.currencies_convert.data

import com.emikhalets.core.database.finance.FinanceLocalDataSource
import com.emikhalets.core.datastore.FinancePrefsDataSource
import com.emikhalets.core.network.CurrencyRemoteDataSource
import com.emikhalets.feature.currencies_convert.domain.model.CurrencyModel
import com.emikhalets.feature.currencies_convert.domain.model.ExchangeModel
import com.emikhalets.feature.currencies_convert.domain.model.filterNeedUpdate
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class Repository @Inject constructor(
    private val localDataSource: FinanceLocalDataSource,
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

//    override suspend fun insertCurrency(code: String): AppResult<Unit> {
//        logi(TAG, "Insert currency: code = $code")
//        return execute {
//            if (!currenciesDao.isExist(code)) {
//                val currency = CurrenciesMapper.mapEntityToDb(CurrencyEntity(code))
//                currenciesDao.insert(currency)
//
//                val currencies = currenciesDao.getAll()
//                val exchanges = exchangesDao.getAll()
//                when (currencies.count()) {
//                    0 -> {
//                    }
//
//                    1 -> {
//                        val entity = ExchangesMapper.mapEntityToDb(ExchangeEntity(code))
//                        exchangesDao.insert(entity)
//                    }
//
//                    2 -> {
//                        val entity = exchanges.first()
//                        val newEntity = entity.copy(code = "${entity.code}$code")
//                        exchangesDao.update(newEntity)
//                    }
//
//                    else -> {
//                        val newExchanges = currencies
//                            .dropLast(1)
//                            .map { ExchangesMapper.mapEntityToDb(ExchangeEntity(it.code, code)) }
//                        exchangesDao.insert(newExchanges)
//                    }
//                }
//            }
//        }
//    }
//
//    override suspend fun deleteCurrency(code: String): AppResult<Unit> {
//        logi(TAG, "Delete currency: code = $code")
//        return execute {
//            currenciesDao.delete(code)
//            exchangesDao.delete(code)
//            val currencies = currenciesDao.getAll()
//            if (currencies.count() == 1) {
//                val lastCode = currencies.first().code
//                exchangesDao.drop()
//                exchangesDao.insert(ExchangesMapper.mapEntityToDb(ExchangeEntity(lastCode)))
//            }
//        }
//    }
}
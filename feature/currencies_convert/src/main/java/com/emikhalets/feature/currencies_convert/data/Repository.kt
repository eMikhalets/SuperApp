package com.emikhalets.feature.currencies_convert.data

import com.emikhalets.core.network.CurrencyRemoteDataSource
import com.emikhalets.feature.currencies_convert.domain.model.CurrencyModel
import com.emikhalets.feature.currencies_convert.domain.model.ExchangeModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository @Inject constructor(
//    private val localDataSource: NotesLocalDataSource,
    private val remoteDataSource: CurrencyRemoteDataSource,
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
//
//    override suspend fun getCurrencies(): AppResult<Flow<List<CurrencyEntity>>> {
//        logi(TAG, "Get currencies")
//        return execute {
//            currenciesDao.getAllFlow()
//                .map { CurrenciesMapper.mapDbListToEntityList(it).toMutableList() }
//        }
//    }
//
//    override suspend fun getExchanges(): AppResult<List<ExchangeEntity>> {
//        logi(TAG, "Get exchanges")
//        return execute {
//            val exchanges = exchangesDao.getAll()
//            val result = ExchangesMapper.mapDbListToEntityList(exchanges).toMutableList()
//            val needUpdateList = result.filterNeedUpdate()
//            if (needUpdateList.isNotEmpty()) {
//                updateExchanges(result)
//            } else {
//                result
//            }
//        }
//    }
//
//    private suspend fun updateExchanges(list: List<ExchangeEntity>): List<ExchangeEntity> {
//        logi(TAG, "Update exchanges")
//        val date = Date().time
//        val result = CurrencyParser().replaceExchangesValues(list, date)
//        dataStore.setCurrenciesDate(date)
//        exchangesDao.update(ExchangesMapper.mapEntityListToDbList(result))
//        return result
//    }
}
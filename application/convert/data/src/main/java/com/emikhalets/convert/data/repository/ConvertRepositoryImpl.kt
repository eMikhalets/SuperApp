package com.emikhalets.convert.data.repository

import com.emikhalets.convert.data.CurrencyParser
import com.emikhalets.convert.data.database.table_currencies.CurrenciesDao
import com.emikhalets.convert.data.database.table_exchanges.ExchangesDao
import com.emikhalets.convert.data.mappers.CurrenciesMapper
import com.emikhalets.convert.data.mappers.ExchangesMapper
import com.emikhalets.convert.domain.ConvertDataStore
import com.emikhalets.convert.domain.entity.CurrencyEntity
import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.convert.domain.entity.filterNeedUpdate
import com.emikhalets.convert.domain.repository.ConvertRepository
import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.execute
import com.emikhalets.core.common.logi
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConvertRepositoryImpl @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    private val exchangesDao: ExchangesDao,
    private val dataStore: ConvertDataStore,
) : ConvertRepository {

    override suspend fun insertCurrency(code: String): AppResult<Unit> {
        logi(TAG, "Insert currency: code = $code")
        return execute {
            if (!currenciesDao.isExist(code)) {
                val currency = CurrenciesMapper.mapEntityToDb(CurrencyEntity(code))
                currenciesDao.insert(currency)

                val currencies = currenciesDao.getAll()
                val exchanges = exchangesDao.getAll()
                when (currencies.count()) {
                    0 -> {
                    }

                    1 -> {
                        val entity = ExchangesMapper.mapEntityToDb(ExchangeEntity(code))
                        exchangesDao.insert(entity)
                    }

                    2 -> {
                        val entity = exchanges.first()
                        val newEntity = entity.copy(code = "${entity.code}$code")
                        exchangesDao.update(newEntity)
                    }

                    else -> {
                        val newExchanges = currencies
                            .dropLast(1)
                            .map { ExchangesMapper.mapEntityToDb(ExchangeEntity(it.code, code)) }
                        exchangesDao.insert(newExchanges)
                    }
                }
            }
        }
    }

    override suspend fun deleteCurrency(code: String): AppResult<Unit> {
        logi(TAG, "Delete currency: code = $code")
        return execute {
            currenciesDao.delete(code)
            exchangesDao.delete(code)
            val currencies = currenciesDao.getAll()
            if (currencies.count() == 1) {
                val lastCode = currencies.first().code
                exchangesDao.insert(ExchangesMapper.mapEntityToDb(ExchangeEntity(lastCode)))
            }
        }
    }

    override suspend fun getCurrencies(): AppResult<Flow<List<CurrencyEntity>>> {
        logi(TAG, "Get currencies")
        return execute {
            currenciesDao.getAllFlow()
                .map { CurrenciesMapper.mapDbListToEntityList(it).toMutableList() }
        }
    }

    override suspend fun getExchanges(): AppResult<List<ExchangeEntity>> {
        logi(TAG, "Get exchanges")
        return execute {
            val exchanges = exchangesDao.getAll()
            val result = ExchangesMapper.mapDbListToEntityList(exchanges).toMutableList()
            val needUpdateList = result.filterNeedUpdate()
            if (needUpdateList.isNotEmpty()) {
                updateExchanges(result)
            } else {
                result
            }
        }
    }

    private suspend fun updateExchanges(list: List<ExchangeEntity>): List<ExchangeEntity> {
        logi(TAG, "Update exchanges")
        val date = Date().time
        val result = CurrencyParser().replaceExchangesValues(list, date)
        dataStore.setCurrenciesDate(date)
        exchangesDao.update(ExchangesMapper.mapEntityListToDbList(result))
        return result
    }

    companion object {

        private const val TAG = "NotesRepository"
    }
}

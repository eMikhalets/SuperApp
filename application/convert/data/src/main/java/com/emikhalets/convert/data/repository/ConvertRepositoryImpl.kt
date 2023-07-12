package com.emikhalets.convert.data.repository

import com.emikhalets.convert.data.CurrencyParser
import com.emikhalets.convert.data.database.table_exchanges.ExchangesDao
import com.emikhalets.convert.data.mappers.ExchangesMapper
import com.emikhalets.convert.domain.ConvertDataStore
import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.convert.domain.entity.getCurrencies
import com.emikhalets.convert.domain.repository.ConvertRepository
import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.execute
import com.emikhalets.core.common.logi
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConvertRepositoryImpl @Inject constructor(
    private val exchangesDao: ExchangesDao,
    private val dataStore: ConvertDataStore,
) : ConvertRepository {

    override suspend fun insertCurrency(code: String): AppResult<Unit> {
        logi(TAG, "Insert currency: code = $code")
        return execute {
            val exchanges = exchangesDao.getAll()
            if (exchanges.map { it.main }.contains(code)) {
                return AppResult.success(Unit)
            }
            if (exchanges.count() == 1 && exchanges.first().secondary.isBlank()) {
                exchangesDao.update(exchanges.first().copy(secondary = code))
            } else {
                val newExchanges = exchanges
                    .map { ExchangesMapper.mapDbToEntity(it) }
                    .getCurrencies()
                    .map { ExchangeEntity(it, code) }
                    .ifEmpty { listOf(ExchangeEntity(code, "")) }
                exchangesDao.insert(ExchangesMapper.mapEntityListToDbList(newExchanges))
            }
        }
    }

    override suspend fun deleteCurrency(code: String): AppResult<Unit> {
        logi(TAG, "Delete currency: code = $code")
        return execute {
            val exchanges = exchangesDao.getAll()
            if (exchanges.count() == 1 && exchanges.first().secondary.isNotBlank()) {
                dataStore.setCurrenciesDate(0)
                exchangesDao.update(exchanges.first().copy(secondary = ""))
            } else {
                exchangesDao.delete(code)
            }
        }
    }

    override suspend fun getCurrenciesExchange(): AppResult<Flow<List<ExchangeEntity>>> {
        logi(TAG, "Get exchanges")
        return execute {
            exchangesDao.getAllFlow().map {
                val result = ExchangesMapper.mapDbListToEntityList(it).toMutableList()
                updateCurrencies(result)
                result
            }
        }
    }

    private suspend fun updateCurrencies(result: MutableList<ExchangeEntity>) {
        val needUpdate = result
            .any { item -> item.secondaryCurrency.isNotBlank() && item.isOldValue() }
        logi(TAG, "Need update exchanges: $needUpdate")
        if (needUpdate) {
            val date = Date().time
            CurrencyParser().getExchanges(result, date)
            dataStore.setCurrenciesDate(date)
        }
    }

    companion object {

        private const val TAG = "NotesRepository"
    }
}

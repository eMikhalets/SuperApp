package com.emikhalets.convert.data.repository

import com.emikhalets.convert.data.database.table_exchanges.ExchangesDao
import com.emikhalets.convert.data.mappers.ExchangesMapper
import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.convert.domain.repository.ConvertRepository
import com.emikhalets.core.common.AppResult
import com.emikhalets.core.common.execute
import com.emikhalets.core.common.logi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class ConvertRepositoryImpl @Inject constructor(
    private val exchangesDao: ExchangesDao,
) : ConvertRepository {

    override suspend fun insertCurrency(code: String): AppResult<Unit> {
        logi(TAG, "Insert currency: code = $code")
        return execute {
            val newExchanges = mutableListOf<ExchangeEntity>()
            val currencies = mutableSetOf<String>()
            val exchanges = exchangesDao.getAllFlow().firstOrNull()
            exchanges?.forEach {
                if (it.main.isNotBlank()) currencies.add(it.main)
                if (it.secondary.isNotBlank()) currencies.add(it.secondary)
            }
            if (currencies.count() == 1) {
                exchanges?.first()?.copy(secondary = code)?.let { exchangesDao.insert(it) }
                return AppResult.success(Unit)
            }
            currencies.forEach { currency ->
                if (currency != code) {
                    newExchanges.add(ExchangeEntity(currency, code))
                }
            }
            exchangesDao.insert(ExchangesMapper.mapEntityListToDbList(newExchanges))
        }
    }

    override suspend fun deleteCurrency(code: String): AppResult<Unit> {
        logi(TAG, "Delete currency: code = $code")
        return execute { exchangesDao.delete(code) }
    }

    override fun getCurrenciesExchange(): AppResult<Flow<List<ExchangeEntity>>> {
        logi(TAG, "Get exchanges")
        return execute {
            exchangesDao.getAllFlow().map { ExchangesMapper.mapDbListToEntityList(it) }
        }
    }

    companion object {

        private const val TAG = "NotesRepository"
    }
}

package com.emikhalets.superapp.feature.convert.data

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.invoke
import com.emikhalets.superapp.core.common.map
import com.emikhalets.superapp.core.database.convert.table_exchanges.ExchangesDao
import com.emikhalets.superapp.core.network.CurrencyParser
import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class ConvertRepositoryImpl @Inject constructor(
    private val currencyParser: CurrencyParser,
    private val exchangesDao: ExchangesDao,
) : ConvertRepository {

    override fun getExchanges(): Flow<List<ExchangeModel>> {
        Timber.d("getExchanges")
        return exchangesDao.getAllFlow().mapToModel()
    }

    override suspend fun updateExchanges(data: List<ExchangeModel>): AppResult<Boolean> {
        val ids = data.joinToString { it.id.toString() }
        Timber.d("updateExchanges with ids $ids")
        return invoke {
            val updatedCount = exchangesDao.update(data.mapToDb())
            updatedCount > 0
        }
    }

    override suspend fun updateExchange(data: ExchangeModel): AppResult<Boolean> {
        Timber.d("updateExchange with id ${data.id}")
        return invoke {
            val updatedCount = exchangesDao.update(data.mapToDb())
            updatedCount == 1
        }
    }

    override suspend fun insertExchange(data: ExchangeModel): AppResult<Boolean> {
        Timber.d("insertExchange with id ${data.id}")
        return invoke {
            val insertId = exchangesDao.insert(data.mapToDb())
            insertId != 0L
        }
    }

    override suspend fun deleteExchanges(data: List<ExchangeModel>): AppResult<Boolean> {
        val ids = data.joinToString { it.id.toString() }
        Timber.d("deleteExchanges with ids $ids")
        return invoke {
            val deletedCount = exchangesDao.delete(data.mapToDb())
            deletedCount > 0
        }
    }

    override suspend fun loadRemoteExchanges(
        data: List<String>,
    ): AppResult<List<Pair<String, Double>>> {
        Timber.d("loadRemoteExchanges")
        val codes = list.map { it.code }
        return invoke { currencyParser.parseCurrencyPairs(codes) }
    }
}
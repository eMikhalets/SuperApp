package com.emikhalets.convert.data

import com.emikhalets.convert.domain.model.CurrencyModel
import com.emikhalets.convert.domain.model.ExchangeModel
import com.emikhalets.core.database.LocalResult
import com.emikhalets.core.database.convert.ConvertLocalDataSource
import com.emikhalets.core.database.convert.table_currencies.CurrencyDb
import com.emikhalets.core.database.convert.table_exchanges.ExchangeDb
import com.emikhalets.core.database.invokeLocal
import com.emikhalets.core.database.map
import com.emikhalets.core.network.CurrencyPair
import com.emikhalets.core.network.CurrencyRemoteDataSource
import com.emikhalets.core.network.RemoteResult
import com.emikhalets.core.network.invokeRemote
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class Repository @Inject constructor(
    private val localDataSource: ConvertLocalDataSource,
    private val remoteDataSource: CurrencyRemoteDataSource,
) {

    // Exchanges

    fun getExchanges(): Flow<List<ExchangeModel>> {
        Timber.d("getExchanges()")
        val result = localDataSource.getExchangesFlow()
        return ExchangeMapper.toModelFlow(result)
    }

    suspend fun updateExchanges(list: List<ExchangeModel>): LocalResult<Int> {
        Timber.d("updateExchanges($list)")
        return invokeLocal { localDataSource.updateExchanges(ExchangeMapper.toDbList(list)) }
    }

    suspend fun deleteExchanges(code: String): LocalResult<Unit> {
        Timber.d("deleteExchanges($code)")
        return invokeLocal { localDataSource.deleteExchanges(code) }
    }

    suspend fun insertFirstExchange(code: String): LocalResult<Unit> {
        Timber.d("insertFirstExchange($code)")
        return invokeLocal {
            val exchange = ExchangeMapper.toDb(ExchangeModel(code))
            localDataSource.dropExchanges()
            localDataSource.insertExchange(exchange)
        }
    }

    suspend fun parseExchanges(list: List<ExchangeModel>): RemoteResult<List<CurrencyPair>> {
        Timber.d("parseExchanges($list)")
        val codes = list.map { it.fullCode }
        return invokeRemote { remoteDataSource.parseExchanges(codes) }
    }

    suspend fun checkExchangesPostInsert(code: String): LocalResult<Unit> {
        Timber.d("checkExchangesPostInsert($code)")
        return invokeLocal {
            val currencies = localDataSource.getCurrencies()
            val exchanges = localDataSource.getExchanges()
            when (currencies.count()) {
                0 -> Unit
                1 -> localDataSource.insertExchange(ExchangeMapper.toDb(ExchangeModel(code)))
                2 -> localDataSource.updateExchange(exchanges.setFirstSubCode(code))
                else -> localDataSource.insertExchanges(currencies.createExchanges(code))
            }
        }
    }

    // Currencies

    fun getCurrencies(): Flow<List<CurrencyModel>> {
        Timber.d("getCurrencies()")
        val result = localDataSource.getCurrenciesFlow()
        return CurrencyMapper.toModelFlow(result)
    }

    suspend fun getCurrenciesSync(): LocalResult<List<CurrencyModel>> {
        Timber.d("getCurrenciesSync()")
        return invokeLocal { localDataSource.getCurrencies() }
            .map { CurrencyMapper.toModelList(it) }
    }

    suspend fun isCodeExist(code: String): LocalResult<Boolean> {
        Timber.d("isCodeExist($code)")
        return invokeLocal { localDataSource.isCurrencyExist(code) }
    }

    suspend fun insertCode(code: String): LocalResult<Long> {
        Timber.d("insertCode($code)")
        val model = CurrencyMapper.toDb(CurrencyModel(code))
        return invokeLocal { localDataSource.insertCurrency(model) }
    }

    suspend fun deleteCurrency(code: String): LocalResult<Unit> {
        Timber.d("deleteCurrency($code)")
        return invokeLocal { localDataSource.deleteCurrency(code) }
    }

    // Utils

    private fun List<ExchangeDb>.setFirstSubCode(code: String): ExchangeDb {
        return first().copy(sub = code)
    }

    private fun List<CurrencyDb>.createExchanges(code: String): List<ExchangeDb> {
        return dropLast(1).map { ExchangeMapper.toDb(ExchangeModel(it.code, code)) }
    }
}
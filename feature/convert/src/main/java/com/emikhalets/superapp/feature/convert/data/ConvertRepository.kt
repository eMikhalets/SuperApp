package com.emikhalets.superapp.feature.convert.data

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.invoke
import com.emikhalets.superapp.core.common.map
import com.emikhalets.superapp.core.common.model.CurrencyValueModel
import com.emikhalets.superapp.core.database.convert.table_currencies.CurrenciesDao
import com.emikhalets.superapp.core.database.convert.table_currencies.CurrencyDb
import com.emikhalets.superapp.core.database.convert.table_currency_pair.CurrencyPairDao
import com.emikhalets.superapp.core.database.convert.table_currency_pair.CurrencyPairDb
import com.emikhalets.superapp.core.network.CurrencyParser
import com.emikhalets.superapp.feature.convert.domain.CurrencyModel
import com.emikhalets.superapp.feature.convert.domain.CurrencyPairModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class ConvertRepository @Inject constructor(
    private val currencyParser: CurrencyParser,
    private val currenciesDao: CurrenciesDao,
    private val currencyPairDao: CurrencyPairDao,
) {

    // CurrencyPairs

    fun getCurrencyPairs(): Flow<List<CurrencyPairModel>> {
        Timber.d("getCurrencyPairs()")
        val result = currencyPairDao.getAllFlow()
        return CurrencyPairMapper.toModelFlow(result)
    }

    suspend fun updateCurrencyPairs(list: List<CurrencyPairModel>): AppResult<Int> {
        Timber.d("updateCurrencyPairs($list)")
        return invoke { currencyPairDao.update(CurrencyPairMapper.toDbList(list)) }
    }

    suspend fun deleteCurrencyPairs(code: String): AppResult<Unit> {
        Timber.d("deleteCurrencyPairs($code)")
        return invoke { currencyPairDao.deleteByCode(code) }
    }

    suspend fun insertFirstCurrencyPairs(code: String): AppResult<Unit> {
        Timber.d("insertFirstCurrencyPairs($code)")
        return invoke {
            val exchange = CurrencyPairMapper.toDb(CurrencyPairModel(code))
            currencyPairDao.drop()
            currencyPairDao.insert(exchange)
        }
    }

    suspend fun parseCurrencyPairs(
        list: List<CurrencyPairModel>,
    ): AppResult<List<CurrencyValueModel>> {
        Timber.d("parseCurrencyPairs($list)")
        val codes = list.map { it.code }
        return invoke { currencyParser.parseCurrencyPairs(codes) }
    }

    suspend fun checkCurrencyPairsPostInsert(code: String): AppResult<Unit> {
        Timber.d("checkCurrencyPairsPostInsert($code)")
        return invoke {
            val currencies = currenciesDao.getAll()
            val exchanges = currencyPairDao.getAll()
            when (currencies.count()) {
                0 -> Unit
                1 -> currencyPairDao.insert(CurrencyPairMapper.toDb(CurrencyPairModel(code)))
                2 -> currencyPairDao.update(exchanges.setFirstSubCode(code))
                else -> currencyPairDao.insert(currencies.createExchanges(code))
            }
        }
    }

    // Currencies

    fun getCurrencies(): Flow<List<CurrencyModel>> {
        Timber.d("getCurrencies()")
        val result = currenciesDao.getAllFlow()
        return CurrencyMapper.toModelFlow(result)
    }

    suspend fun getCurrenciesSync(): AppResult<List<CurrencyModel>> {
        Timber.d("getCurrenciesSync()")
        return invoke { currenciesDao.getAll() }
            .map { CurrencyMapper.toModelList(it) }
    }

    suspend fun isCodeExist(code: String): AppResult<Boolean> {
        Timber.d("isCodeExist($code)")
        return invoke { currenciesDao.isCodeExist(code) }
    }

    suspend fun insertCode(code: String): AppResult<Long> {
        Timber.d("insertCode($code)")
        val model = CurrencyMapper.toDb(CurrencyModel(code))
        return invoke { currenciesDao.insert(model) }
    }

    suspend fun deleteCurrency(code: String): AppResult<Unit> {
        Timber.d("deleteCurrency($code)")
        return invoke { currenciesDao.deleteByCode(code) }
    }

    // Utils

    private fun List<CurrencyPairDb>.setFirstSubCode(code: String): CurrencyPairDb {
        return first().copy(sub = code)
    }

    private fun List<CurrencyDb>.createExchanges(code: String): List<CurrencyPairDb> {
        return dropLast(1).map { CurrencyPairMapper.toDb(CurrencyPairModel(it.code, code)) }
    }
}
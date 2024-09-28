package com.emikhalets.superapp.feature.convert

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ConvertRepositoryTestImpl : ConvertRepository {

    private var exchangesFlow: MutableStateFlow<List<ExchangeModel>> = MutableStateFlow(emptyList())
    private var exchangesRemote: List<Pair<String, Double>> = emptyList()

    fun setExchangesFlow(list: List<ExchangeModel>) {
        exchangesFlow.tryEmit(list)
    }

    fun setExchangesRemote(list: List<Pair<String, Double>>) {
        exchangesRemote = list
    }

    override fun getExchanges(): Flow<List<ExchangeModel>> {
        return exchangesFlow
    }

    override suspend fun getExchangesSync(): AppResult<List<ExchangeModel>> {
        return AppResult.success(exchangesFlow.value)
    }

    override suspend fun updateExchanges(data: List<ExchangeModel>): AppResult<Boolean> {
        exchangesFlow.tryEmit(
            exchangesFlow.value.map { exchange ->
                data.find { it.id == exchange.id } ?: exchange
            }
        )
        return AppResult.success(true)
    }

    override suspend fun updateExchange(data: ExchangeModel): AppResult<Boolean> {
        exchangesFlow.tryEmit(
            exchangesFlow.value.map { exchange ->
                if (exchange.id == data.id) data else exchange
            }
        )
        return AppResult.success(true)
    }

    override suspend fun insertExchange(data: ExchangeModel): AppResult<Boolean> {
        exchangesFlow.tryEmit(
            buildList {
                addAll(exchangesFlow.value)
                val lastId = exchangesFlow.value.lastOrNull()?.id ?: 0
                add(data.copy(id = lastId + 1))
            }
        )
        return AppResult.success(true)
    }

    override suspend fun insertExchanges(data: List<ExchangeModel>): AppResult<Boolean> {
        exchangesFlow.tryEmit(
            buildList {
                addAll(exchangesFlow.value)
                var lastId = exchangesFlow.value.lastOrNull()?.id ?: 0
                addAll(data.map { it.copy(id = ++lastId) })
            }
        )
        return AppResult.success(true)
    }

    override suspend fun deleteExchanges(code: String): AppResult<Boolean> {
        exchangesFlow.tryEmit(
            exchangesFlow.value.filter { exchange ->
                exchange.mainCode != code && exchange.subCode != code
            }
        )
        return AppResult.success(true)
    }

    override suspend fun deleteExchange(data: ExchangeModel): AppResult<Boolean> {
        exchangesFlow.tryEmit(
            exchangesFlow.value.filter { exchange ->
                data.id != exchange.id
            }
        )
        return AppResult.success(true)
    }

    override suspend fun isCodeExist(code: String): AppResult<Boolean> {
        return AppResult.success(
            exchangesFlow.value.any { exchange ->
                exchange.mainCode == code || exchange.subCode == code
            }
        )
    }

    override suspend fun loadRemoteExchanges(
        data: List<String>,
    ): AppResult<List<Pair<String, Double>>> {
        return AppResult.success(exchangesRemote)
    }
}
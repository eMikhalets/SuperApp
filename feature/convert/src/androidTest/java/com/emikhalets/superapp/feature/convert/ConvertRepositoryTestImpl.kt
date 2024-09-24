package com.emikhalets.superapp.feature.convert

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.feature.convert.domain.ConvertRepository
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.lastOrNull

class ConvertRepositoryTestImpl : ConvertRepository {

    var exchangesFlow = MutableSharedFlow<List<ExchangeModel>>()
    override fun getExchanges(): Flow<List<ExchangeModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getExchangesSync(): AppResult<List<ExchangeModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateExchanges(data: List<ExchangeModel>): AppResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateExchange(data: ExchangeModel): AppResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun insertExchange(data: ExchangeModel): AppResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun insertExchanges(data: List<ExchangeModel>): AppResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExchanges(code: String): AppResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun isCodeExist(code: String): AppResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun loadRemoteExchanges(data: List<String>): AppResult<List<Pair<String, Double>>> {
        TODO("Not yet implemented")
    }
}
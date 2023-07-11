package com.emikhalets.convert.domain.repository

import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.core.common.AppResult
import kotlinx.coroutines.flow.Flow

interface ConvertRepository {

    suspend fun insertCurrency(code: String): AppResult<Unit>

    suspend fun deleteCurrency(code: String): AppResult<Unit>

    suspend fun getCurrenciesExchange(): AppResult<Flow<List<ExchangeEntity>>>
}
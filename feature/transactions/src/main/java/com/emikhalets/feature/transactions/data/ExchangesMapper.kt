package com.emikhalets.feature.currencies_convert.data

import com.emikhalets.core.database.finance.table_exchanges.ExchangeDb
import com.emikhalets.feature.currencies_convert.domain.model.ExchangeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@JvmName("toExchangesDbFlow")
fun Flow<List<ExchangeModel>>.toDbFlow(): Flow<List<ExchangeDb>> = map { it.toDbList() }

@JvmName("toExchangesModelFlow")
fun Flow<List<ExchangeDb>>.toModelFlow(): Flow<List<ExchangeModel>> = map { it.toModelList() }

@JvmName("toExchangesDbList")
fun List<ExchangeModel>.toDbList(): List<ExchangeDb> = map { it.toDb() }

@JvmName("toExchangesModelList")
fun List<ExchangeDb>.toModelList(): List<ExchangeModel> = map { it.toModel() }

@JvmName("toExchangeDb")
fun ExchangeModel.toDb(): ExchangeDb = ExchangeDb(
    id = id,
    code = code,
    value = value,
    date = date
)

@JvmName("toExchangeModel")
fun ExchangeDb.toModel(): ExchangeModel = ExchangeModel(
    id = id,
    code = code,
    value = value,
    date = date
)
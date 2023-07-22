package com.emikhalets.feature.currencies_convert.data

import com.emikhalets.core.database.finance.table_exchanges.ExchangeDb
import com.emikhalets.feature.currencies_convert.domain.model.ExchangeModel

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
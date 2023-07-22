package com.emikhalets.feature.currencies_convert.data

import com.emikhalets.core.database.finance.table_currencies.CurrencyDb
import com.emikhalets.feature.currencies_convert.domain.model.CurrencyModel

@JvmName("toCurrenciesDbList")
fun List<CurrencyModel>.toDbList(): List<CurrencyDb> = map { it.toDb() }

@JvmName("toCurrenciesModelList")
fun List<CurrencyDb>.toModelList(): List<CurrencyModel> = map { it.toModel() }

@JvmName("toCurrencyDb")
fun CurrencyModel.toDb(): CurrencyDb = CurrencyDb(
    id = id,
    code = code,
    symbol = "",
    name = ""
)

@JvmName("toCurrencyModel")
fun CurrencyDb.toModel(): CurrencyModel = CurrencyModel(
    id = id,
    code = code,
)
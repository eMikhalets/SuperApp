package com.emikhalets.feature.currencies_convert.data

import com.emikhalets.core.database.finance.table_currencies.CurrencyDb
import com.emikhalets.feature.currencies_convert.domain.model.CurrencyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@JvmName("toCurrenciesDbFlow")
fun Flow<List<CurrencyModel>>.toDbFlow(): Flow<List<CurrencyDb>> = map { it.toDbList() }

@JvmName("toCurrenciesDbFlow")
fun Flow<List<CurrencyDb>>.toModelFlow(): Flow<List<CurrencyModel>> = map { it.toModelList() }

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
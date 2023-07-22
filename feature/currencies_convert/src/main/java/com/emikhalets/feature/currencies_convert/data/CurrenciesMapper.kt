package com.emikhalets.feature.currencies_convert.data

import com.emikhalets.core.database.finance.table_convert_currencies.ConvertCurrencyDb
import com.emikhalets.feature.currencies_convert.domain.model.CurrencyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@JvmName("toCurrenciesDbFlow")
fun Flow<List<CurrencyModel>>.toDbFlow(): Flow<List<ConvertCurrencyDb>> = map { it.toDbList() }

@JvmName("toCurrenciesDbFlow")
fun Flow<List<ConvertCurrencyDb>>.toModelFlow(): Flow<List<CurrencyModel>> =
    map { it.toModelList() }

@JvmName("toCurrenciesDbList")
fun List<CurrencyModel>.toDbList(): List<ConvertCurrencyDb> = map { it.toDb() }

@JvmName("toCurrenciesModelList")
fun List<ConvertCurrencyDb>.toModelList(): List<CurrencyModel> = map { it.toModel() }

@JvmName("toCurrencyDb")
fun CurrencyModel.toDb(): ConvertCurrencyDb = ConvertCurrencyDb(
    id = id,
    code = code,
)

@JvmName("toCurrencyModel")
fun ConvertCurrencyDb.toModel(): CurrencyModel = CurrencyModel(
    id = id,
    code = code,
)
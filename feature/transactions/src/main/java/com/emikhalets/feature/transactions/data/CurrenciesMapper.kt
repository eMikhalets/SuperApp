package com.emikhalets.feature.currencies_convert.data

import com.emikhalets.core.database.finance.table_convert_currencies.ConvertCurrencyDb
import com.emikhalets.feature.transactions.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@JvmName("toCurrenciesDbFlow")
fun Flow<List<TransactionModel>>.toDbFlow(): Flow<List<ConvertCurrencyDb>> = map { it.toDbList() }

@JvmName("toCurrenciesModelFlow")
fun Flow<List<ConvertCurrencyDb>>.toModelFlow(): Flow<List<TransactionModel>> =
    map { it.toModelList() }

@JvmName("toCurrenciesDbList")
fun List<TransactionModel>.toDbList(): List<ConvertCurrencyDb> = map { it.toDb() }

@JvmName("toCurrenciesModelList")
fun List<ConvertCurrencyDb>.toModelList(): List<TransactionModel> = map { it.toModel() }

@JvmName("toCurrencyDb")
fun TransactionModel.toDb(): ConvertCurrencyDb = ConvertCurrencyDb(
    id = id,
    code = code,
)

@JvmName("toCurrencyModel")
fun ConvertCurrencyDb.toModel(): TransactionModel = TransactionModel(
    id = id,
    code = code,
)
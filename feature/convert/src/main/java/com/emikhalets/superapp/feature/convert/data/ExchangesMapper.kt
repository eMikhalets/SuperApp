package com.emikhalets.superapp.feature.convert.data

import com.emikhalets.superapp.core.database.convert.table_exchanges.ExchangeDb
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun ExchangeModel.mapToDb(): ExchangeDb {
    return ExchangeDb(
        id = this.id,
        mainCode = this.mainCode,
        subCode = this.subCode,
        value = this.value,
        updateDate = this.updateDate
    )
}

fun List<ExchangeModel>.mapToDb(): List<ExchangeDb> {
    return this.map { it.mapToDb() }
}

fun ExchangeDb.mapToModel(): ExchangeModel {
    return ExchangeModel(
        id = this.id,
        mainCode = this.mainCode,
        subCode = this.subCode,
        value = this.value,
        updateDate = this.updateDate
    )
}

fun List<ExchangeDb>.mapToModel(): List<ExchangeModel> {
    return this.map { it.mapToModel() }
}

fun Flow<List<ExchangeDb>>.mapToModel(): Flow<List<ExchangeModel>> {
    return this.map { it.mapToModel() }
}
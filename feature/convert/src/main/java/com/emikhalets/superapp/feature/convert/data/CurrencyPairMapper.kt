package com.emikhalets.superapp.feature.convert.data

import com.emikhalets.superapp.core.database.convert.table_currency_pair.CurrencyPairDb
import com.emikhalets.superapp.feature.convert.domain.CurrencyPairModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object CurrencyPairMapper {

    fun toDb(model: CurrencyPairModel): CurrencyPairDb {
        return CurrencyPairDb(
            id = model.id,
            main = model.mainCode,
            sub = model.subCode,
            value = model.value,
            date = model.date
        )
    }

    fun toDbList(list: List<CurrencyPairModel>): List<CurrencyPairDb> {
        return list.map { toDb(it) }
    }

    fun toDbFlow(flow: Flow<List<CurrencyPairModel>>): Flow<List<CurrencyPairDb>> {
        return flow.map { toDbList(it) }
    }

    fun toModel(model: CurrencyPairDb): CurrencyPairModel {
        return CurrencyPairModel(
            id = model.id,
            mainCode = model.main,
            subCode = model.sub,
            value = model.value,
            date = model.date
        )
    }

    fun toModelList(list: List<CurrencyPairDb>): List<CurrencyPairModel> {
        return list.map { toModel(it) }
    }

    fun toModelFlow(flow: Flow<List<CurrencyPairDb>>): Flow<List<CurrencyPairModel>> {
        return flow.map { toModelList(it) }
    }
}
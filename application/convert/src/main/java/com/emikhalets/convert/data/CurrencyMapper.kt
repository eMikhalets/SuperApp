package com.emikhalets.convert.data

import com.emikhalets.convert.domain.model.CurrencyModel
import com.emikhalets.core.database.convert.table_currencies.CurrencyDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object CurrencyMapper {

    fun toDb(model: CurrencyModel): CurrencyDb {
        return CurrencyDb(
            id = model.id,
            code = model.code,
        )
    }

    fun toDbList(list: List<CurrencyModel>): List<CurrencyDb> {
        return list.map { toDb(it) }
    }

    fun toDbFlow(flow: Flow<List<CurrencyModel>>): Flow<List<CurrencyDb>> {
        return flow.map { toDbList(it) }
    }

    fun toModel(model: CurrencyDb): CurrencyModel {
        return CurrencyModel(
            id = model.id,
            code = model.code,
        )
    }

    fun toModelList(list: List<CurrencyDb>): List<CurrencyModel> {
        return list.map { toModel(it) }
    }

    fun toModelFlow(flow: Flow<List<CurrencyDb>>): Flow<List<CurrencyModel>> {
        return flow.map { toModelList(it) }
    }
}
package com.emikhalets.superapp.data.salary.mapper

import com.emikhalets.superapp.data.salary.table_currencies.CurrencyDb
import com.emikhalets.superapp.domain.convert.CurrencyModel
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
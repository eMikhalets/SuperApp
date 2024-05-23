package com.emikhalets.convert.data

import com.emikhalets.convert.domain.model.ExchangeModel
import com.emikhalets.core.database.convert.table_exchanges.ExchangeDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ExchangeMapper {

    fun toDb(model: ExchangeModel): ExchangeDb {
        return ExchangeDb(
            id = model.id,
            main = model.main,
            sub = model.sub,
            value = model.value,
            date = model.date
        )
    }

    fun toDbList(list: List<ExchangeModel>): List<ExchangeDb> {
        return list.map { toDb(it) }
    }

    fun toDbFlow(flow: Flow<List<ExchangeModel>>): Flow<List<ExchangeDb>> {
        return flow.map { toDbList(it) }
    }

    fun toModel(model: ExchangeDb): ExchangeModel {
        return ExchangeModel(
            id = model.id,
            main = model.main,
            sub = model.sub,
            value = model.value,
            date = model.date
        )
    }

    fun toModelList(list: List<ExchangeDb>): List<ExchangeModel> {
        return list.map { toModel(it) }
    }

    fun toModelFlow(flow: Flow<List<ExchangeDb>>): Flow<List<ExchangeModel>> {
        return flow.map { toModelList(it) }
    }
}
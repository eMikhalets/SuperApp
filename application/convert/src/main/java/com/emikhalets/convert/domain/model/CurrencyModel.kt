package com.emikhalets.convert.domain.model

import com.emikhalets.core.database.convert.table_currencies.CurrencyDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class CurrencyModel(
    val id: Long,
    val code: String,
) {

    constructor(code: String) : this(0, code)

    companion object {

        fun CurrencyModel.toDb(): CurrencyDb {
            return CurrencyDb(
                id = id,
                code = code,
            )
        }

        fun List<CurrencyModel>.toDbList(): List<CurrencyDb> {
            return map { it.toDb() }
        }

        fun Flow<List<CurrencyModel>>.toDbFlow(): Flow<List<CurrencyDb>> {
            return map { it.toDbList() }
        }

        fun CurrencyDb.toModel(): CurrencyModel {
            return CurrencyModel(
                id = id,
                code = code,
            )
        }

        fun List<CurrencyDb>.toModelList(): List<CurrencyModel> {
            return map { it.toModel() }
        }

        fun Flow<List<CurrencyDb>>.toModelFlow(): Flow<List<CurrencyModel>> {
            return map { it.toModelList() }
        }
    }
}

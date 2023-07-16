package com.emikhalets.convert.data.mappers

import com.emikhalets.convert.data.database.table_currencies.CurrencyDb
import com.emikhalets.convert.domain.entity.CurrencyEntity
import com.emikhalets.core.common.logi

object CurrenciesMapper {

    private const val TAG = "CurrenciesMapper"

    fun mapDbToEntity(entity: CurrencyDb): CurrencyEntity {
        logi(TAG, "Db To Entity: entity = $entity")
        val result = CurrencyEntity(
            id = entity.id,
            code = entity.code
        )
        logi(TAG, "Result = $result")
        return result
    }

    fun mapDbListToEntityList(list: List<CurrencyDb>): List<CurrencyEntity> {
        return list.map { mapDbToEntity(it) }
    }

    fun mapEntityToDb(entity: CurrencyEntity): CurrencyDb {
        logi(TAG, "Entity To Db: entity = $entity")
        val result = CurrencyDb(
            id = entity.id,
            code = entity.code
        )
        logi(TAG, "Result = $result")
        return result
    }

    fun mapEntityListToDbList(list: List<CurrencyEntity>): List<CurrencyDb> {
        return list.map { mapEntityToDb(it) }
    }
}
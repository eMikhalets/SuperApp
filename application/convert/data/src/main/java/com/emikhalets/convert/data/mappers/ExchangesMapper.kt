package com.emikhalets.convert.data.mappers

import com.emikhalets.convert.data.database.table_exchanges.ExchangeDb
import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.core.common.logi

object ExchangesMapper {

    private const val TAG = "ExchangesMapper"

    fun mapDbToEntity(entity: ExchangeDb): ExchangeEntity {
        logi(TAG, "Db To Entity: entity = $entity")
        val result = ExchangeEntity(
            id = entity.id,
            code = entity.code,
            value = entity.value,
            date = entity.date
        )
        logi(TAG, "Result = $result")
        return result
    }

    fun mapDbListToEntityList(list: List<ExchangeDb>): List<ExchangeEntity> {
        return list.map { mapDbToEntity(it) }
    }

    fun mapEntityToDb(entity: ExchangeEntity): ExchangeDb {
        logi(TAG, "Entity To Db: entity = $entity")
        val result = ExchangeDb(
            id = entity.id,
            code = entity.code,
            value = entity.value,
            date = entity.date
        )
        logi(TAG, "Result = $result")
        return result
    }

    fun mapEntityListToDbList(list: List<ExchangeEntity>): List<ExchangeDb> {
        return list.map { mapEntityToDb(it) }
    }
}
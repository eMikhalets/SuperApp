package com.emikhalets.events.data.mapper

import com.emikhalets.events.data.database.table.alarm.AlarmDb
import com.emikhalets.events.domain.entity.AlarmEntity

object AlarmMapper {

    fun mapDbToEntity(entity: AlarmDb): AlarmEntity = AlarmEntity(
        id = entity.id,
        enabled = entity.enabled,
        milliseconds = entity.milliseconds
    )

    fun mapEntityToDb(entity: AlarmEntity): AlarmDb = AlarmDb(
        id = entity.id,
        enabled = entity.enabled,
        milliseconds = entity.milliseconds
    )

    fun mapDbListToList(list: List<AlarmDb>): List<AlarmEntity> = list.map {
        mapDbToEntity(it)
    }

    fun mapListToDbList(list: List<AlarmEntity>): List<AlarmDb> = list.map {
        mapEntityToDb(it)
    }
}

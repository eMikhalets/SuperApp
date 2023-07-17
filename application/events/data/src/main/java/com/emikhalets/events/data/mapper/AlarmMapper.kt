package com.emikhalets.events.data.mapper

import com.emikhalets.events.data.database.table.alarm.AlarmDb
import com.emikhalets.events.domain.entity.AlarmEntity

object AlarmMapper {

    fun mapDbToEntity(entity: AlarmDb): AlarmEntity = AlarmEntity(
        id = entity.id,
        nameEn = entity.nameEn,
        enabled = entity.enabled,
        days = entity.days
    )

    fun mapEntityToDb(entity: AlarmEntity): AlarmDb = AlarmDb(
        id = entity.id,
        nameEn = entity.nameEn,
        enabled = entity.enabled,
        days = entity.days
    )

    fun mapDbListToList(list: List<AlarmDb>): List<AlarmEntity> = list.map {
        mapDbToEntity(it)
    }

    fun mapListToDbList(list: List<AlarmEntity>): List<AlarmDb> = list.map {
        mapEntityToDb(it)
    }
}

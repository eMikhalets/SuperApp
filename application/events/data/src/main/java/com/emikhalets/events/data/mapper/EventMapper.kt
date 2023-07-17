package com.emikhalets.events.data.mapper

import com.emikhalets.common.DateHelper
import com.emikhalets.events.data.database.table.events.EventDb
import com.emikhalets.events.domain.entity.EventEntity

object EventMapper {

    fun mapDbToEntity(entity: EventDb): EventEntity = EventEntity(
        id = entity.id,
        date = entity.date,
        name = entity.name,
        eventType = entity.eventType,
        note = entity.note,
        withoutYear = entity.withoutYear,
        groupId = entity.groupId,
        days = DateHelper.daysLeft(entity.date),
        age = DateHelper.turns(entity.date)
    )

    fun mapEntityToDb(entity: EventEntity): EventDb = EventDb(
        id = entity.id,
        date = entity.date,
        name = entity.name,
        eventType = entity.eventType,
        note = entity.note,
        withoutYear = entity.withoutYear,
        groupId = entity.groupId
    )

    fun mapDbListToList(list: List<EventDb>): List<EventEntity> = list.map {
        mapDbToEntity(it)
    }

    fun mapListToDbList(list: List<EventEntity>): List<EventDb> = list.map {
        mapEntityToDb(it)
    }
}

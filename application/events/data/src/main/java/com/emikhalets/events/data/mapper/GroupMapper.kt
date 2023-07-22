package com.emikhalets.events.data.mapper

import com.emikhalets.events.data.database.table.groups.GroupDb
import com.emikhalets.events.domain.entity.GroupEntity

object GroupMapper {

    fun mapDbToEntity(entity: GroupDb): GroupEntity = GroupEntity(
        id = entity.id,
        name = entity.name,
        isAlarmsEnabled = entity.isAlarmsEnabled
    )

    fun mapEntityToDb(entity: GroupEntity): GroupDb = GroupDb(
        id = entity.id,
        name = entity.name,
        isAlarmsEnabled = entity.isAlarmsEnabled
    )

    fun mapDbListToList(list: List<GroupDb>): List<GroupEntity> = list.map {
        mapDbToEntity(it)
    }

    fun mapListToDbList(list: List<GroupEntity>): List<GroupDb> = list.map {
        mapEntityToDb(it)
    }
}

package com.emikhalets.events.domain.entity

import com.emikhalets.common.DateHelper
import java.time.LocalDate
import kotlin.random.Random

object PreviewEntity {

    fun getEventEntity(id: Long): EventEntity {
        val date = DateHelper.timestamp(LocalDate.now())
        return EventEntity(
            id = id,
            name = "Event Name $id",
            date = date,
            eventType = EventType.BIRTHDAY,
            note = "",
            withoutYear = false,
            groupId = 0,
            days = DateHelper.daysLeft(date),
            age = DateHelper.turns(date)
        )
    }

    fun getEventsList(size: Int): List<EventEntity> {
        val date = DateHelper.timestamp(LocalDate.now())
        return List(size) {
            EventEntity(
                id = it + 1L,
                name = "Event Name ${it + 1L}",
                date = date,
                eventType = EventType.BIRTHDAY,
                note = "",
                withoutYear = false,
                groupId = 0,
                days = DateHelper.daysLeft(date),
                age = DateHelper.turns(date)
            )
        }
    }

    fun getGroupEntity(id: Long): GroupEntity {
        return GroupEntity(
            id = id,
            name = "Name id",
            isAlarmsEnabled = Random.nextBoolean()
        )
    }

    fun getEventListScreenEvents(): Map<Long, List<EventEntity>> {
        return mapOf(
            DateHelper.timestamp(LocalDate.now()) to listOf(
                getEventEntity(1),
                getEventEntity(2),
                getEventEntity(3),
                getEventEntity(4),
            ),
            DateHelper.timestamp(LocalDate.now().minusMonths(1)) to listOf(
                getEventEntity(1),
                getEventEntity(2),
            ),
            DateHelper.timestamp(LocalDate.now().minusMonths(2)) to listOf(
                getEventEntity(1),
                getEventEntity(2),
                getEventEntity(3),
                getEventEntity(4),
                getEventEntity(5),
            ),
        )
    }

    fun getGroupsList(): List<GroupEntity> {
        return List(10) { getGroupEntity(it + 1L) }
    }
}

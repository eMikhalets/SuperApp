package com.emikhalets.fitness.presentation.screens.events_list

import com.emikhalets.common.DateHelper
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.entity.EventType
import com.emikhalets.events.domain.entity.GroupEntity
import java.time.LocalDate
import kotlin.random.Random

fun previewEventsListScreen(): List<EventEntity> {
    return listOf(
        EventEntity(
            id = 0,
            groupId = 0,
            name = "Event Name",
            type = EventType.BIRTHDAY,
            date = DateHelper.timestampOf(2000, 5, 13),
            note = "",
            isWithYear = false,
            isAlarm = true,
            isHide = false,
            days = 2,
            age = 15
        ),
        EventEntity(
            id = 0,
            groupId = 0,
            name = "Event Name",
            type = EventType.BIRTHDAY,
            date = DateHelper.timestampOf(2000, 5, 13),
            note = "",
            isWithYear = false,
            isAlarm = true,
            isHide = false,
            days = 5,
            age = 15
        ),
        EventEntity(
            id = 0,
            groupId = 0,
            name = "Event Name",
            type = EventType.BIRTHDAY,
            date = DateHelper.timestampOf(2000, 5, 13),
            note = "",
            isWithYear = false,
            isAlarm = true,
            isHide = false,
            days = 70,
            age = 15
        ),
    )
}

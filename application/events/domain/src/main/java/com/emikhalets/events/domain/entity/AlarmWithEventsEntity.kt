package com.emikhalets.events.domain.entity

data class AlarmWithEventsEntity(
    val alarm: AlarmEntity,
    val events: List<EventEntity>,
)

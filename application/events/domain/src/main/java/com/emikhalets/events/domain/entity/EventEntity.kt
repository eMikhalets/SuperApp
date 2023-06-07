package com.emikhalets.events.domain.entity

data class EventEntity(
    val id: Long,
    val groupId: Long,
    val name: String,
    val type: EventType,
    val date: Long,
    val note: String,
    val isWithYear: Boolean,
    val isHide: Boolean,
    val isAlarm: Boolean,
    val days: Int,
    val age: Int,
)

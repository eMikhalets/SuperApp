package com.emikhalets.events.domain.entity

data class EventEntity(
    val id: Long,
    val date: Long,
    val name: String,
    val eventType: EventType,
    val note: String,
    val withoutYear: Boolean,
    val groupId: Long,
    val days: Int,
    val age: Int,
)

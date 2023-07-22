package com.emikhalets.events.domain.entity

data class GroupEntity(
    val id: Long,
    val name: String,
    val isAlarmsEnabled: Boolean,
)

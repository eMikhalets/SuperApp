package com.emikhalets.events.domain.entity

data class AlarmEntity(
    val id: Long,
    val enabled: Boolean,
    val milliseconds: Long,
)

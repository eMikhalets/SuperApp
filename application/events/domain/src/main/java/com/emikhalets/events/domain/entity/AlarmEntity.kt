package com.emikhalets.events.domain.entity

data class AlarmEntity(
    val id: Long,
    val nameEn: String,
    val enabled: Boolean,
    val days: Int,
)

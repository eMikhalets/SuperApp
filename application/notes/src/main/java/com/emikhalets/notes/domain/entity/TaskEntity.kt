package com.emikhalets.notes.domain.entity

data class TaskEntity(
    val id: Long,
    val content: String,
    val isCompleted: Boolean,
    val createTimestamp: Long,
    val updateTimestamp: Long,
)

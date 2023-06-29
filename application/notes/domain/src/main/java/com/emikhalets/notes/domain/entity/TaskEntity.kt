package com.emikhalets.notes.domain.entity

import java.util.Date

data class TaskEntity(
    val id: Long,
    val content: String,
    val isCompleted: Boolean,
    val createTimestamp: Long,
    val updateTimestamp: Long,
) {

    constructor(content: String, isCompleted: Boolean = false) : this(
        id = 0,
        content = content,
        isCompleted = isCompleted,
        createTimestamp = Date().time,
        updateTimestamp = Date().time
    )
}

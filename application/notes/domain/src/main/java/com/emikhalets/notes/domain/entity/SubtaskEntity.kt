package com.emikhalets.notes.domain.entity

import java.util.Date

data class SubtaskEntity(
    val id: Long,
    val taskId: Long,
    val content: String,
    val isCompleted: Boolean,
    val createTimestamp: Long,
    val updateTimestamp: Long,
) {

    constructor(taskId: Long, content: String, isCompleted: Boolean = false) : this(
        id = 0,
        taskId = taskId,
        content = content,
        isCompleted = isCompleted,
        createTimestamp = Date().time,
        updateTimestamp = Date().time
    )
}

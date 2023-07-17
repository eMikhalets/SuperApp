package com.emikhalets.notes.domain.entity

import java.util.Date

data class TaskEntity(
    val id: Long,
    val content: String,
    val isCompleted: Boolean,
    val createTimestamp: Long,
    val updateTimestamp: Long,
    val subtasks: List<SubtaskEntity>,
) {

    val subtasksCount: Int = subtasks.count()
    val subtasksCompletedCount: Int = subtasks.count { it.isCompleted }

    constructor(
        content: String,
        isCompleted: Boolean = false,
        subtasks: List<SubtaskEntity> = emptyList(),
    ) : this(0, content, isCompleted, subtasks)

    constructor(
        id: Long,
        content: String,
        isCompleted: Boolean = false,
        subtasks: List<SubtaskEntity> = emptyList(),
    ) : this(
        id = id,
        content = content,
        isCompleted = isCompleted,
        createTimestamp = Date().time,
        updateTimestamp = Date().time,
        subtasks = subtasks
    )
}

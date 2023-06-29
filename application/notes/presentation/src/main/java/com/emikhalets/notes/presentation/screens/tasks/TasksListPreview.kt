package com.emikhalets.notes.presentation.screens.tasks

import com.emikhalets.notes.domain.entity.TaskEntity

fun getTasksListPreview(): List<TaskEntity> {
    return listOf(
        TaskEntity("Note title"),
        TaskEntity("Note title"),
        TaskEntity("Note title", isCompleted = true),
        TaskEntity("Note title"),
        TaskEntity("Note title"),
        TaskEntity("Note title"),
    )
}
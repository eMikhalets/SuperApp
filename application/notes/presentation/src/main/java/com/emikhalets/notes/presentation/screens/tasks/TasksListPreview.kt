package com.emikhalets.notes.presentation.screens.tasks

import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.entity.TaskEntity

fun getTasksListPreview(completed: Boolean): List<TaskEntity> {
    val subtasks = listOf(
        SubtaskEntity(0, "Subtask Content"),
        SubtaskEntity(0, "Subtask Content"),
        SubtaskEntity(0, "Subtask Content"),
    )
    return listOf(
        TaskEntity("Task Content", subtasks = subtasks, isCompleted = completed),
        TaskEntity("Task Content Task Content Task Content Task Content Task Content Task Content Task Content", isCompleted = completed),
        TaskEntity("Task Content", isCompleted = completed),
        TaskEntity("Task Content", isCompleted = completed),
    )
}
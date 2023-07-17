package com.emikhalets.notes.presentation.screens.tasks

import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.entity.TaskEntity

fun getTasksListPreview(): List<TaskEntity> {
    val subtasks = listOf(
        SubtaskEntity(5, "Subtask Content"),
        SubtaskEntity(6, "Subtask Content"),
        SubtaskEntity(7, "Subtask Content"),
    )
    return listOf(
        TaskEntity(1, "Task Content", subtasks = subtasks),
        TaskEntity(
            2,
            "Task Content Task Content Task Content Task Content Task Content Task Content Task Content",
        ),
        TaskEntity(3, "Task Content"),
        TaskEntity(4, "Task Content"),
    )
}

fun getCheckedTasksListPreview(): List<TaskEntity> {
    return listOf(
        TaskEntity(8, "Task Content", isCompleted = true),
        TaskEntity(
            9,
            "Task Content Task Content Task Content Task Content Task Content Task Content Task Content",
            isCompleted = true
        ),
    )
}
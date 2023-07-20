package com.emikhalets.feature.tasks.presentation

import com.emikhalets.feature.tasks.data.TaskModel

fun getTasksListPreview(): List<TaskModel> {
    return listOf(
        TaskModel(
            id = 1,
            content = "Task Content",
            subtasks = listOf(
                TaskModel(2, "Subtask Content"),
                TaskModel(3, "Subtask Content"),
                TaskModel(4, "Subtask Content"),
            )
        ),
        TaskModel(
            id = 5,
            content = "Task Content Task Content Task Content Task Content Task Content Task Content Task Content",
        ),
        TaskModel(3, "Task Content"),
        TaskModel(4, "Task Content"),
    )
}

fun getCheckedTasksListPreview(): List<TaskModel> {
    return listOf(
        TaskModel(8, "Task Content", true),
        TaskModel(
            id = 9,
            content = "Task Content Task Content Task Content Task Content Task Content Task Content Task Content",
            completed = true
        ),
    )
}
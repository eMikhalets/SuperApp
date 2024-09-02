package com.emikhalets.superapp.feature.notes.ui.task_list

import com.emikhalets.superapp.core.common.timestamp
import com.emikhalets.superapp.feature.notes.domain.TaskModel

internal fun getTasksListPreview(): List<TaskModel> {
    return listOf(
        TaskModel(
            id = 1,
            parentId = 0,
            content = "test",
            completed = false,
            createDate = timestamp(),
            updateDate = timestamp(),
            subtasks = listOf(
                TaskModel(
                    id = 2,
                    parentId = 1,
                    content = "test test test test test test test test test test test test test test test test test test test test ",
                    completed = false,
                    createDate = timestamp(),
                    updateDate = timestamp(),
                    subtasks = emptyList(),
                ),
                TaskModel(
                    id = 4,
                    parentId = 1,
                    content = "test",
                    completed = false,
                    createDate = timestamp(),
                    updateDate = timestamp(),
                    subtasks = emptyList(),
                ),
                TaskModel(
                    id = 3,
                    parentId = 1,
                    content = "test",
                    completed = true,
                    createDate = timestamp(),
                    updateDate = timestamp(),
                    subtasks = emptyList(),
                ),
            )
        ),
        TaskModel(
            id = 5,
            parentId = 0,
            content = "test",
            completed = true,
            createDate = timestamp(),
            updateDate = timestamp(),
            subtasks = listOf(
                TaskModel(
                    id = 6,
                    parentId = 5,
                    content = "test",
                    completed = false,
                    createDate = timestamp(),
                    updateDate = timestamp(),
                    subtasks = emptyList(),
                ),
                TaskModel(
                    id = 7,
                    parentId = 5,
                    content = "test",
                    completed = false,
                    createDate = timestamp(),
                    updateDate = timestamp(),
                    subtasks = emptyList(),
                ),
                TaskModel(
                    id = 8,
                    parentId = 5,
                    content = "test",
                    completed = false,
                    createDate = timestamp(),
                    updateDate = timestamp(),
                    subtasks = emptyList(),
                ),
            )
        )
    )
}
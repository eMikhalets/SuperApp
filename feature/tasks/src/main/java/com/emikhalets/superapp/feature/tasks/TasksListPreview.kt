package com.emikhalets.superapp.feature.tasks

import com.emikhalets.superapp.core.common.date.DateHelper
import com.emikhalets.superapp.domain.tasks.TaskModel

internal fun getTasksListPreview(): List<TaskModel> {
    return listOf(
        TaskModel(
            id = 1,
            parentId = 0,
            content = "test",
            completed = false,
            createDate = DateHelper.now,
            updateDate = DateHelper.now,
            subtasks = listOf(
                TaskModel(
                    id = 2,
                    parentId = 1,
                    content = "test test test test test test test test test test test test test test test test test test test test ",
                    completed = false,
                    createDate = DateHelper.now,
                    updateDate = DateHelper.now,
                    subtasks = emptyList(),
                ),
                TaskModel(
                    id = 4,
                    parentId = 1,
                    content = "test",
                    completed = false,
                    createDate = DateHelper.now,
                    updateDate = DateHelper.now,
                    subtasks = emptyList(),
                ),
                TaskModel(
                    id = 3,
                    parentId = 1,
                    content = "test",
                    completed = true,
                    createDate = DateHelper.now,
                    updateDate = DateHelper.now,
                    subtasks = emptyList(),
                ),
            )
        ),
        TaskModel(
            id = 5,
            parentId = 0,
            content = "test",
            completed = true,
            createDate = DateHelper.now,
            updateDate = DateHelper.now,
            subtasks = listOf(
                TaskModel(
                    id = 6,
                    parentId = 5,
                    content = "test",
                    completed = false,
                    createDate = DateHelper.now,
                    updateDate = DateHelper.now,
                    subtasks = emptyList(),
                ),
                TaskModel(
                    id = 7,
                    parentId = 5,
                    content = "test",
                    completed = false,
                    createDate = DateHelper.now,
                    updateDate = DateHelper.now,
                    subtasks = emptyList(),
                ),
                TaskModel(
                    id = 8,
                    parentId = 5,
                    content = "test",
                    completed = false,
                    createDate = DateHelper.now,
                    updateDate = DateHelper.now,
                    subtasks = emptyList(),
                ),
            )
        )
    )
}
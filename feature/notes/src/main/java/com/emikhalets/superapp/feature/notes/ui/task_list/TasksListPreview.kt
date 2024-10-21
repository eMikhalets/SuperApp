package com.emikhalets.superapp.feature.notes.ui.task_list

import com.emikhalets.superapp.core.common.timestamp
import com.emikhalets.superapp.feature.notes.domain.SubTaskModel
import com.emikhalets.superapp.feature.notes.domain.TaskModel

internal fun getTasksListPreview(): List<TaskModel> {
    return listOf(
        TaskModel(
            id = 1,
            header = "test",
            createDate = timestamp(),
            subtasks = listOf(
                SubTaskModel(
                    id = 1,
                    parentId = 1,
                    text = "test test test test test test test test test test test test test test test test test test test test ",
                    completed = false,
                    createDate = timestamp(),
                ),
                SubTaskModel(
                    id = 2,
                    parentId = 1,
                    text = "test",
                    completed = false,
                    createDate = timestamp(),
                ),
                SubTaskModel(
                    id = 3,
                    parentId = 1,
                    text = "test",
                    completed = true,
                    createDate = timestamp(),
                ),
            )
        ),
        TaskModel(
            id = 2,
            header = "test",
            createDate = timestamp(),
            subtasks = listOf(
                SubTaskModel(
                    id = 4,
                    parentId = 2,
                    text = "test",
                    completed = false,
                    createDate = timestamp(),
                ),
                SubTaskModel(
                    id = 5,
                    parentId = 2,
                    text = "test",
                    completed = false,
                    createDate = timestamp(),
                ),
                SubTaskModel(
                    id = 6,
                    parentId = 2,
                    text = "test",
                    completed = false,
                    createDate = timestamp(),
                ),
            )
        )
    )
}
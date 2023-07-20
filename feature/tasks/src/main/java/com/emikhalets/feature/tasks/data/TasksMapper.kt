package com.emikhalets.feature.tasks.data

import com.emikhalets.core.database.notes.embeded.TaskFullDb
import com.emikhalets.core.database.notes.table_tasks.TaskDb

fun List<TaskModel>.toDbList(parentId: Long): List<TaskDb> = map { it.toDb(parentId) }

@JvmName("toModelListFromFull")
fun List<TaskFullDb>.toModelList(): List<TaskModel> = map { it.toModel() }

@JvmName("toModelList")
fun List<TaskDb>.toModelList(): List<TaskModel> = map { it.toModel() }

fun TaskModel.toDb(parentId: Long = 0): TaskDb = TaskDb(
    id = id,
    parentId = parentId,
    content = content,
    completed = completed,
    createDate = createDate,
    updateDate = updateDate,
)

fun TaskFullDb.toModel(): TaskModel = TaskModel(
    id = task.id,
    content = task.content,
    completed = task.completed,
    createDate = task.createDate,
    updateDate = task.updateDate,
    subtasks = subtasks.map { it.toModel() }
)

fun TaskDb.toModel(subtasks: List<TaskModel> = emptyList()): TaskModel = TaskModel(
    id = id,
    content = content,
    completed = completed,
    createDate = createDate,
    updateDate = updateDate,
    subtasks = subtasks
)
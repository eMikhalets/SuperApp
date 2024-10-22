package com.emikhalets.superapp.feature.notes.data

import com.emikhalets.superapp.core.database.notes.embeded.TaskFullDb
import com.emikhalets.superapp.core.database.notes.table_subtasks.SubTaskDb
import com.emikhalets.superapp.core.database.notes.table_tasks.TaskDb
import com.emikhalets.superapp.feature.notes.domain.SubTaskModel
import com.emikhalets.superapp.feature.notes.domain.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun TaskFullDb.toModel(): TaskModel {
    return TaskModel(
        id = this.task.id,
        header = this.task.header,
        createDate = this.task.createDate,
        subtasks = this.subtasks.map { it.toModel() },
    )
}

fun SubTaskDb.toModel(): SubTaskModel {
    return SubTaskModel(
        id = this.id,
        parentId = this.parentId,
        text = this.text,
        completed = this.completed,
        createDate = this.createDate,
    )
}

@JvmName("toModelListTaskFullDb")
fun List<TaskFullDb>.toModel(): List<TaskModel> {
    return this.map { it.toModel() }
}

@JvmName("toModelFlowTaskFullDb")
fun Flow<List<TaskFullDb>>.toModel(): Flow<List<TaskModel>> {
    return this.map { it.toModel() }
}

fun TaskModel.toDb(): TaskDb {
    return TaskDb(
        id = this.id,
        header = this.header,
        createDate = this.createDate,
    )
}

fun SubTaskModel.toDb(): SubTaskDb {
    return SubTaskDb(
        id = this.id,
        text = this.text,
        createDate = this.createDate,
        parentId = this.parentId,
        completed = this.completed,
    )
}
package com.emikhalets.superapp.feature.notes.data

import com.emikhalets.superapp.core.database.notes.embeded.TaskFullDb
import com.emikhalets.superapp.core.database.notes.table_tasks.TaskDb
import com.emikhalets.superapp.feature.notes.domain.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun TaskModel.toDb(): TaskDb {
    return TaskDb(
        id = this.id,
        header = this.header,
        createDate = this.createDate,
    )
}

object TasksMapper {

    fun toFullDb(model: TaskModel): TaskFullDb {
        return TaskFullDb(
            task = toDb(model),
            subtasks = emptyList()// toDbList(model.subtasks, model.id),
        )
    }

    fun toDb(model: TaskModel, parentId: Long? = null): TaskDb {
        return TaskDb(
            id = model.id,
            parentId = 0,
            content = "",
            completed = true,
            createDate = model.createDate,
            updateDate = model.createDate,
        )
    }

    fun toDbList(list: List<TaskModel>, parentId: Long? = null): List<TaskDb> {
        return list.map { toDb(it, parentId) }
    }

    fun toModel(model: TaskFullDb): TaskModel {
        return TaskModel(
//            id = model.task.id,
//            parentId = model.task.parentId,
//            content = model.task.content,
//            completed = model.task.completed,
//            createDate = model.task.createDate,
//            updateDate = model.task.updateDate,
//            subtasks = toSubModelList(model.subtasks),
        )
    }

    fun toModelList(list: List<TaskFullDb>): List<TaskModel> {
        return list.map { toModel(it) }
    }

    fun toModelFlow(flow: Flow<List<TaskFullDb>>): Flow<List<TaskModel>> {
        return flow.map { toModelList(it) }
    }

    fun toSubModel(model: TaskDb): TaskModel {
        return TaskModel(
//            id = model.id,
//            parentId = model.parentId,
//            content = model.content,
//            completed = model.completed,
//            createDate = model.createDate,
//            updateDate = model.updateDate,
//            subtasks = emptyList(),
        )
    }

    fun toSubModelList(list: List<TaskDb>): List<TaskModel> {
        return list.map { toSubModel(it) }
    }
}
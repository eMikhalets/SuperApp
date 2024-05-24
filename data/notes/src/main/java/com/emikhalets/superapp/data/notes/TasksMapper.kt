package com.emikhalets.superapp.data.notes

import com.emikhalets.superapp.data.notes.embeded.TaskFullDb
import com.emikhalets.superapp.data.notes.table_tasks.TaskDb
import com.emikhalets.superapp.domain.notes.model.SubtaskModel
import com.emikhalets.superapp.domain.notes.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object TasksMapper {

    fun toFullDb(model: TaskModel): TaskFullDb {
        return TaskFullDb(
            task = toDb(model),
            subtasks = toDbList(model.subtasks, model.id),
        )
    }

    fun toDb(model: TaskModel): TaskDb {
        return TaskDb(
            id = model.id,
            parentId = 0,
            content = model.content,
            completed = model.completed,
            createDate = model.createDate,
            updateDate = model.updateDate,
        )
    }

    fun toDb(model: SubtaskModel, parentId: Long? = null): TaskDb {
        return TaskDb(
            id = model.id,
            parentId = parentId ?: model.parentId,
            content = model.content,
            completed = model.completed,
            createDate = model.createDate,
            updateDate = model.updateDate,
        )
    }

    fun toDbList(list: List<SubtaskModel>, parentId: Long? = null): List<TaskDb> {
        return list.map { toDb(it, parentId) }
    }

    fun toModel(model: TaskFullDb): TaskModel {
        return TaskModel(
            id = model.task.id,
            content = model.task.content,
            completed = model.task.completed,
            createDate = model.task.createDate,
            updateDate = model.task.updateDate,
            subtasks = toSubModelList(model.subtasks),
        )
    }

    fun toModelList(list: List<TaskFullDb>): List<TaskModel> {
        return list.map { toModel(it) }
    }

    fun toModelFlow(flow: Flow<List<TaskFullDb>>): Flow<List<TaskModel>> {
        return flow.map { toModelList(it) }
    }

    fun toSubModel(model: TaskDb): SubtaskModel {
        return SubtaskModel(
            id = model.id,
            parentId = model.parentId,
            content = model.content,
            completed = model.completed,
            createDate = model.createDate,
            updateDate = model.updateDate,
        )
    }

    fun toSubModelList(list: List<TaskDb>): List<SubtaskModel> {
        return list.map { toSubModel(it) }
    }
}
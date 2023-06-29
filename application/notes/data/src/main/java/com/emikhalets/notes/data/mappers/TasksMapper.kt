package com.emikhalets.notes.data.mappers

import com.emikhalets.notes.data.database.embeded.TaskWithSubtasksDb
import com.emikhalets.notes.data.database.table_tasks.TaskDb
import com.emikhalets.notes.domain.entity.TaskEntity

object TasksMapper {

    fun mapDbToEntity(entity: TaskWithSubtasksDb): TaskEntity = TaskEntity(
        id = entity.task.id,
        content = entity.task.content,
        isCompleted = entity.task.isCompleted,
        createTimestamp = entity.task.createTimestamp,
        updateTimestamp = entity.task.updateTimestamp,
        subtasks = SubtasksMapper.mapDbListToEntityList(entity.subtasks)
    )

    fun mapDbListToEntityList(list: List<TaskWithSubtasksDb>): List<TaskEntity> =
        list.map { mapDbToEntity(it) }

    fun mapEntityToDb(entity: TaskEntity): TaskDb = TaskDb(
        id = entity.id,
        content = entity.content,
        isCompleted = entity.isCompleted,
        createTimestamp = entity.createTimestamp,
        updateTimestamp = entity.updateTimestamp,
    )

    fun mapEntityListToDbList(list: List<TaskEntity>): List<TaskDb> =
        list.map { mapEntityToDb(it) }
}
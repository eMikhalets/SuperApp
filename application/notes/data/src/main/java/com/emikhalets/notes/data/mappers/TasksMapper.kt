package com.emikhalets.notes.data.mappers

import com.emikhalets.core.common.logi
import com.emikhalets.notes.data.database.embeded.TaskWithSubtasksDb
import com.emikhalets.notes.data.database.table_tasks.TaskDb
import com.emikhalets.notes.domain.entity.TaskEntity

object TasksMapper {

    private const val TAG = "TasksMapper"

    fun mapDbToEntity(entity: TaskWithSubtasksDb): TaskEntity {
        logi(TAG, "mapDbToEntity(): entity = $entity")
        return TaskEntity(
            id = entity.task.id,
            content = entity.task.content,
            isCompleted = entity.task.isCompleted,
            createTimestamp = entity.task.createTimestamp,
            updateTimestamp = entity.task.updateTimestamp,
            subtasks = SubtasksMapper.mapDbListToEntityList(entity.subtasks)
        )
    }

    fun mapDbListToEntityList(list: List<TaskWithSubtasksDb>): List<TaskEntity> {
        logi(TAG, "mapDbListToEntityList(): size = ${list.count()}")
        return list.map { mapDbToEntity(it) }
    }

    fun mapEntityToDb(entity: TaskEntity): TaskDb {
        logi(TAG, "mapEntityToDb(): entity = $entity")
        return TaskDb(
            id = entity.id,
            content = entity.content,
            isCompleted = entity.isCompleted,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )
    }

    fun mapEntityListToDbList(list: List<TaskEntity>): List<TaskDb> {
        logi(TAG, "mapEntityListToDbList(): size = ${list.count()}")
        return list.map { mapEntityToDb(it) }
    }
}
package com.emikhalets.notes.data.mappers

import com.emikhalets.core.common.logi
import com.emikhalets.notes.data.database.table_subtasks.SubtaskDb
import com.emikhalets.notes.domain.entity.SubtaskEntity

object SubtasksMapper {

    private const val TAG = "SubtasksMapper"

    fun mapDbToEntity(entity: SubtaskDb): SubtaskEntity {
        logi(TAG, "mapDbToEntity(): entity = $entity")
        return SubtaskEntity(
            id = entity.id,
            taskId = entity.taskId,
            content = entity.content,
            isCompleted = entity.isCompleted,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )
    }

    fun mapDbListToEntityList(list: List<SubtaskDb>): List<SubtaskEntity> {
        logi(TAG, "mapDbListToEntityList(): size = ${list.count()}")
        return list.map { mapDbToEntity(it) }
    }

    fun mapEntityToDb(entity: SubtaskEntity): SubtaskDb {
        logi(TAG, "mapEntityToDb(): entity = $entity")
        return SubtaskDb(
            id = entity.id,
            taskId = entity.taskId,
            content = entity.content,
            isCompleted = entity.isCompleted,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )
    }

    fun mapEntityListToDbList(list: List<SubtaskEntity>): List<SubtaskDb> {
        logi(TAG, "mapDbListToEntityList(): size = ${list.count()}")
        return list.map { mapEntityToDb(it) }
    }
}
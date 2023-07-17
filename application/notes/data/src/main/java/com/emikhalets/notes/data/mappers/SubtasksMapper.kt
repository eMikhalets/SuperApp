package com.emikhalets.notes.data.mappers

import com.emikhalets.core.common.logi
import com.emikhalets.notes.data.database.table_subtasks.SubtaskDb
import com.emikhalets.notes.domain.entity.SubtaskEntity

object SubtasksMapper {

    private const val TAG = "SubtasksMapper"

    fun mapDbToEntity(entity: SubtaskDb): SubtaskEntity {
        logi(TAG, "Db To Entity: entity = $entity")
        val result = SubtaskEntity(
            id = entity.id,
            taskId = entity.taskId,
            content = entity.content,
            isCompleted = entity.isCompleted,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )
        logi(TAG, "Result = $result")
        return result
    }

    fun mapDbListToEntityList(list: List<SubtaskDb>): List<SubtaskEntity> {
        return list.map { mapDbToEntity(it) }
    }

    fun mapEntityToDb(entity: SubtaskEntity): SubtaskDb {
        logi(TAG, "Entity To Db: entity = $entity")
        val result = SubtaskDb(
            id = entity.id,
            taskId = entity.taskId,
            content = entity.content,
            isCompleted = entity.isCompleted,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )
        logi(TAG, "Result = $result")
        return result
    }

    fun mapEntityListToDbList(list: List<SubtaskEntity>): List<SubtaskDb> {
        return list.map { mapEntityToDb(it) }
    }
}
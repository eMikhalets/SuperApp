package com.emikhalets.notes.data.mappers

import com.emikhalets.notes.data.database.table_subtasks.SubtaskDb
import com.emikhalets.notes.domain.entity.SubtaskEntity

object SubtasksMapper {

    fun mapDbToEntity(entity: SubtaskDb): SubtaskEntity =
        SubtaskEntity(
            id = entity.id,
            taskId = entity.taskId,
            content = entity.content,
            isCompleted = entity.isCompleted,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )

    fun mapDbListToEntityList(list: List<SubtaskDb>): List<SubtaskEntity> =
        list.map { mapDbToEntity(it) }

    fun mapEntityToDb(entity: SubtaskEntity): SubtaskDb = SubtaskDb(
        id = entity.id,
        taskId = entity.taskId,
        content = entity.content,
        isCompleted = entity.isCompleted,
        createTimestamp = entity.createTimestamp,
        updateTimestamp = entity.updateTimestamp,
    )

    fun mapEntityListToDbList(list: List<SubtaskEntity>): List<SubtaskDb> =
        list.map { mapEntityToDb(it) }
}
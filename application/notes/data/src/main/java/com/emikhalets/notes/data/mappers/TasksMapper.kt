package com.emikhalets.notes.data.mappers

import com.emikhalets.notes.data.database.table_tasks.TaskDb
import com.emikhalets.notes.domain.entity.TaskEntity

object TasksMapper {

    fun mapDbToEntity(dbEntity: TaskDb): TaskEntity =
        TaskEntity(
            id = dbEntity.id,
            content = dbEntity.content,
            isCompleted = dbEntity.isCompleted,
            createTimestamp = dbEntity.createTimestamp,
            updateTimestamp = dbEntity.updateTimestamp,
        )

    fun mapDbListToEntityList(dbList: List<TaskDb>): List<TaskEntity> =
        dbList.map { mapDbToEntity(it) }

    fun mapEntityToDb(entity: TaskEntity): TaskDb = TaskDb(
        id = entity.id,
        content = entity.content,
        isCompleted = entity.isCompleted,
        createTimestamp = entity.createTimestamp,
        updateTimestamp = entity.updateTimestamp,
    )

    fun mapEntityListToDbList(entityList: List<TaskEntity>): List<TaskDb> =
        entityList.map { mapEntityToDb(it) }
}
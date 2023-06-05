package com.emikhalets.simplenotes.data.mappers

import com.emikhalets.simplenotes.data.database.entities.SubtaskDb
import com.emikhalets.simplenotes.data.database.entities.TaskDb
import com.emikhalets.simplenotes.data.database.entities.TaskWithSubtasksDb
import com.emikhalets.simplenotes.domain.entities.SubtaskEntity
import com.emikhalets.simplenotes.domain.entities.TaskEntity
import javax.inject.Inject

class TasksMapper @Inject constructor() {

    fun mapDbToEntity(dbEntity: TaskWithSubtasksDb): TaskEntity = TaskEntity(
        id = dbEntity.task.id,
        content = dbEntity.task.content,
        checked = dbEntity.task.checked,
        savedTime = dbEntity.task.savedTime,
        subtasks = dbEntity.subtasks.map {
            SubtaskEntity(
                id = it.id,
                taskId = it.taskId,
                content = it.content,
                checked = it.checked,
                savedTime = it.savedTime
            )
        }
    )

    fun mapDbListToEntityList(dbList: List<TaskWithSubtasksDb>): List<TaskEntity> = dbList.map {
        mapDbToEntity(it)
    }

    fun mapEntityToDb(entity: TaskEntity): TaskDb = TaskDb(
        id = entity.id,
        content = entity.content,
        checked = entity.checked,
        savedTime = entity.savedTime
    )

    fun mapSubEntityToDb(entity: SubtaskEntity): SubtaskDb = SubtaskDb(
        id = entity.id,
        taskId = entity.taskId,
        content = entity.content,
        checked = entity.checked,
        savedTime = entity.savedTime
    )

    fun mapEntityListToDbList(entityList: List<TaskEntity>): List<TaskDb> = entityList.map {
        mapEntityToDb(it)
    }
}
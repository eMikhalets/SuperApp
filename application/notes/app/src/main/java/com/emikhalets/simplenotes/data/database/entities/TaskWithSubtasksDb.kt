package com.emikhalets.simplenotes.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithSubtasksDb(
    @Embedded
    val task: TaskDb,
    @Relation(parentColumn = "id", entityColumn = "task_id")
    val subtasks: List<SubtaskDb>,
)

package com.emikhalets.superapp.data.notes.embeded

import androidx.room.Embedded
import androidx.room.Relation
import com.emikhalets.superapp.data.notes.table_tasks.TaskDb

data class TaskFullDb(
    @Embedded
    val task: TaskDb,
    @Relation(parentColumn = "id", entityColumn = "parent_id")
    val subtasks: List<TaskDb>,
)

package com.emikhalets.notes.data.database.embeded

import androidx.room.Embedded
import androidx.room.Relation
import com.emikhalets.notes.data.database.table_subtasks.SubtaskDb
import com.emikhalets.notes.data.database.table_tasks.TaskDb

data class TaskWithSubtasksDb(
    @Embedded val task: TaskDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "task_id"
    )
    val subtasks: List<SubtaskDb>
)

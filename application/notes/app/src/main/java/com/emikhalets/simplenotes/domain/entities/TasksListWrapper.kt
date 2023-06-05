package com.emikhalets.simplenotes.domain.entities

data class TasksListWrapper(
    val task: TaskEntity,
) {

    val subtasksCount: Int = task.subtasks.size
    val subtasksCheckedCount: Int = task.subtasks.count { it.checked }
}

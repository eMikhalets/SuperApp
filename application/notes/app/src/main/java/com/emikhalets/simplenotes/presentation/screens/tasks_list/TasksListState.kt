package com.emikhalets.simplenotes.presentation.screens.tasks_list

import com.emikhalets.simplenotes.domain.entities.TasksListWrapper
import com.emikhalets.simplenotes.utils.UiString

data class TasksListState(
    val tasksList: List<TasksListWrapper> = emptyList(),
    val checkedList: List<TasksListWrapper> = emptyList(),
    val error: UiString? = null,
)

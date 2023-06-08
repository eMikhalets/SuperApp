package com.emikhalets.notes.presentation.screens.tasks

import com.emikhalets.simplenotes.utils.UiString

data class TasksListState(
    val tasksList: List<com.emikhalets.notes.domain.entity.TasksListWrapper> = emptyList(),
    val checkedList: List<com.emikhalets.notes.domain.entity.TasksListWrapper> = emptyList(),
    val error: UiString? = null,
)

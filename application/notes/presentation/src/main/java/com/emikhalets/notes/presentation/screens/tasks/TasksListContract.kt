package com.emikhalets.notes.presentation.screens.tasks

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.entity.TaskEntity

object TasksListContract {

    @Immutable
    sealed class Action : UiAction {

        class DeleteTaskDialog(val task: TaskEntity) : Action()
        class DeleteTask(val task: TaskEntity?) : Action()
        class CompleteTask(val task: TaskEntity?, val complete: Boolean) : Action()
        class CompleteSubtask(val task: SubtaskEntity?, val complete: Boolean) : Action()
        object GetTask : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {

        class DeleteTaskDialog(val entity: TaskEntity) : Effect()
        class Error(val message: UiString?) : Effect()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val tasksList: List<TaskEntity> = emptyList(),
        val checkedList: List<TaskEntity> = emptyList(),
    ) : UiState
}

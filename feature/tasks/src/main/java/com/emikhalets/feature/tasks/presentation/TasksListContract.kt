package com.emikhalets.feature.tasks.presentation

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.feature.tasks.data.TaskModel

object TasksListContract {

    @Immutable
    sealed class Action : UiAction {

        object DropTaskDialog : Action()
        object DropError : Action()
        object SwitchCheckedExpand : Action()
        class TaskClicked(val task: TaskModel = TaskModel()) : Action()
        class CheckTask(val task: TaskModel, val check: Boolean) : Action()
        class SaveTask(val task: TaskModel) : Action()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val tasksList: List<TaskModel> = emptyList(),
        val checkedList: List<TaskModel> = emptyList(),
        val editingTask: TaskModel? = null,
        val isCheckedTasksExpanded: Boolean = false,
        val showTaskDialog: Boolean = false,
        val error: UiString? = null,
    ) : UiState
}

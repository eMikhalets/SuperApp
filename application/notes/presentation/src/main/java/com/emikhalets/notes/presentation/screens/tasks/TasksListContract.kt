package com.emikhalets.notes.presentation.screens.tasks

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.entity.TaskEntity

object TasksListContract {

    @Immutable
    sealed class Action : UiAction {

        object DropError : Action()
        object SwitchCheckedExpand : Action()
        object GetTask : Action()
        class SetEditingTask(val task: TaskEntity = TaskEntity("")) : Action()
        class CheckTask(val task: TaskEntity?, val check: Boolean) : Action()
        class CheckSubtask(val subtask: SubtaskEntity?, val check: Boolean) : Action()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val isTaskDeleted: Boolean = false,
        val tasksList: List<TaskEntity> = emptyList(),
        val checkedList: List<TaskEntity> = emptyList(),
        val editingTask: TaskEntity? = null,
        val isCheckedTasksExpanded: Boolean = false,
        val error: UiString? = null,
    ) : UiState
}

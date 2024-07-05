package com.emikhalets.superapp.feature.notes.ui.task_list

import androidx.compose.runtime.Immutable
import com.emikhalets.superapp.core.common.mvi.MviAction
import com.emikhalets.superapp.core.common.mvi.MviEffect
import com.emikhalets.superapp.core.common.mvi.MviState
import com.emikhalets.superapp.feature.notes.domain.TaskModel

object TasksContract {

    @Immutable
    sealed class Action : MviAction {
        data class SaveEditTask(val model: TaskModel) : Action()
        data class CheckTask(val model: TaskModel) : Action()
        data class SetEditTask(val model: TaskModel = TaskModel()) : Action()
    }

    @Immutable
    sealed class Effect : MviEffect

    @Immutable
    data class State(
        val tasksList: List<TaskModel> = emptyList(),
        val editTask: TaskModel? = null,
    ) : MviState
}

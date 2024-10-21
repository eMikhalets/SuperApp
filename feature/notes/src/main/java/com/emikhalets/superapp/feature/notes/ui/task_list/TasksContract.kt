package com.emikhalets.superapp.feature.notes.ui.task_list

import androidx.compose.runtime.Immutable
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.ui.mvi.MviAction
import com.emikhalets.superapp.core.ui.mvi.MviEffect
import com.emikhalets.superapp.core.ui.mvi.MviState
import com.emikhalets.superapp.feature.notes.domain.SubTaskModel
import com.emikhalets.superapp.feature.notes.domain.TaskModel

object TasksContract {

    @Immutable
    sealed class Action : MviAction {
        data class SaveEditTask(val model: TaskModel) : Action()
        data class CheckTask(val model: SubTaskModel) : Action()
        data class SetEditTask(val model: TaskModel?) : Action()
        data class DeleteTask(val model: TaskModel?) : Action()
        data class SetError(val value: StringValue?) : Action()
    }

    @Immutable
    sealed class Effect : MviEffect

    @Immutable
    data class State(
        val tasksList: List<TaskModel> = emptyList(),
        val editTask: TaskModel? = null,
        val error: StringValue? = null,
    ) : MviState
}

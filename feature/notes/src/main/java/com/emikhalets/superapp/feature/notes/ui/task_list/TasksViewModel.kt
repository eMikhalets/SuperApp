package com.emikhalets.superapp.feature.notes.ui.task_list

import androidx.lifecycle.viewModelScope
import com.emikhalets.superapp.core.common.constant.Const
import com.emikhalets.superapp.core.common.mvi.MviViewModel
import com.emikhalets.superapp.core.common.mvi.launch
import com.emikhalets.superapp.feature.notes.domain.TaskModel
import com.emikhalets.superapp.feature.notes.domain.use_case.DeleteTaskUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.GetTasksUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.InsertTaskUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.UpdateTaskUseCase
import com.emikhalets.superapp.feature.notes.ui.task_list.TasksContract.Action
import com.emikhalets.superapp.feature.notes.ui.task_list.TasksContract.Effect
import com.emikhalets.superapp.feature.notes.ui.task_list.TasksContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : MviViewModel<Action, Effect, State>() {

    private val tasksFlow: Flow<List<TaskModel>> =
        flow { emitAll(getTasksUseCase.invoke()) }
            .catch { setFailureState(it) }
            .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    init {
        launch {
            tasksFlow.collect { setTasksState(it) }
        }
    }

    override fun setInitialState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            is Action.SaveEditTask -> saveTaskEdit(action.model)
            is Action.CheckTask -> changeTaskCompleted(action.model)
            is Action.SetEditTask -> setEditTask(action.model)
        }
    }

    private fun saveTaskEdit(model: TaskModel) {
        launch {
            if (model.id == Const.IdNew) {
                insertTaskUseCase.invoke(model)
            } else {
                updateTaskUseCase.invoke(model)
            }
        }
    }

    private fun changeTaskCompleted(model: TaskModel) {
        launch {
            val checkedModel = model.copy(completed = !model.completed)
            updateTaskUseCase.invoke(checkedModel)
        }
    }

    private fun setEditTask(model: TaskModel) {
        setState { it.copy(editTask = model) }
    }

    private fun setTasksState(list: List<TaskModel>) {
        setState { it.copy(tasksList = list) }
    }

    private fun setFailureState(throwable: Throwable) {
        // TODO show error dialog
    }
}

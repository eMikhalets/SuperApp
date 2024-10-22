package com.emikhalets.superapp.feature.notes.ui.task_list

import androidx.lifecycle.viewModelScope
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.constant.Const
import com.emikhalets.superapp.core.ui.extentions.launch
import com.emikhalets.superapp.core.ui.mvi.MviViewModel
import com.emikhalets.superapp.feature.notes.domain.SubTaskModel
import com.emikhalets.superapp.feature.notes.domain.TaskModel
import com.emikhalets.superapp.feature.notes.domain.use_case.DeleteSubTaskUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.DeleteTaskUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.GetTasksUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.InsertSubTaskUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.InsertTaskUseCase
import com.emikhalets.superapp.feature.notes.domain.use_case.UpdateSubTaskUseCase
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
    private val insertSubTaskUseCase: InsertSubTaskUseCase,
    private val updateSubTaskUseCase: UpdateSubTaskUseCase,
    private val deleteSubTaskUseCase: DeleteSubTaskUseCase,
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
            is Action.SetEditTask -> setEditTask(action.model)
            is Action.SaveTask -> saveTask(action.model)
            is Action.DeleteTask -> deleteTask(action.model)
            is Action.SetEditSubTask -> setEditSubTask(action.model)
            is Action.CheckSubTask -> checkSubTask(action.model)
            is Action.SaveSubTask -> saveSubTask(action.model)
            is Action.DeleteSubTask -> deleteSubTask(action.model)
            is Action.SetError -> dropError(action.value)
        }
    }

    private fun setTasksState(list: List<TaskModel>) {
        setState { it.copy(tasksList = list) }
    }

    private fun setEditTask(model: TaskModel?) {
        setState { it.copy(editTask = model) }
    }

    private fun saveTask(model: TaskModel) {
        launch {
            if (model.id == Const.IdNew) {
                when (val result = insertTaskUseCase.invoke(model)) {
                    is InsertTaskUseCase.Result.Failure -> setFailureState(result.message)
                    InsertTaskUseCase.Result.Success -> Unit
                }
            } else {
                when (val result = updateTaskUseCase.invoke(model)) {
                    is UpdateTaskUseCase.Result.Failure -> setFailureState(result.message)
                    UpdateTaskUseCase.Result.Success -> Unit
                }
            }
        }
    }

    private fun deleteTask(model: TaskModel?) {
        setEditTask(null)
        model ?: return
        launch {
            when (val result = deleteTaskUseCase.invoke(model)) {
                is DeleteTaskUseCase.Result.Failure -> setFailureState(result.message)
                DeleteTaskUseCase.Result.Success -> Unit
            }
        }
    }

    private fun setEditSubTask(model: SubTaskModel?) {
        setState { it.copy(editSubTask = model) }
    }

    private fun checkSubTask(model: SubTaskModel) {
        launch {
            val checkedModel = model.copy(completed = !model.completed)
            when (val result = updateSubTaskUseCase.invoke(checkedModel)) {
                is UpdateSubTaskUseCase.Result.Failure -> setFailureState(result.message)
                UpdateSubTaskUseCase.Result.Success -> Unit
            }
        }
    }

    private fun saveSubTask(model: SubTaskModel) {
        launch {
            if (model.id == Const.IdNew) {
                when (val result = insertSubTaskUseCase.invoke(model)) {
                    is InsertSubTaskUseCase.Result.Failure -> setFailureState(result.message)
                    InsertSubTaskUseCase.Result.Success -> Unit
                }
            } else {
                when (val result = updateSubTaskUseCase.invoke(model)) {
                    is UpdateSubTaskUseCase.Result.Failure -> setFailureState(result.message)
                    UpdateSubTaskUseCase.Result.Success -> Unit
                }
            }
        }
    }

    private fun deleteSubTask(model: SubTaskModel?) {
        setEditSubTask(null)
        model ?: return
        launch {
            when (val result = deleteSubTaskUseCase.invoke(model)) {
                is DeleteSubTaskUseCase.Result.Failure -> setFailureState(result.message)
                DeleteSubTaskUseCase.Result.Success -> Unit
            }
        }
    }

    private fun dropError(value: StringValue?) {
        setState { it.copy(error = value) }
    }

    private fun setFailureState(throwable: Throwable) {
        // TODO show error dialog
    }

    private fun setFailureState(error: StringValue) {
        setState { it.copy(error = error) }
    }
}

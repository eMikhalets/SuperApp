package com.emikhalets.notes.presentation.screens.tasks

import com.emikhalets.core.common.BaseViewModel
import com.emikhalets.core.common.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.core.ui.UiString
import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.domain.usecase.TasksUseCase
import com.emikhalets.notes.presentation.screens.tasks.TasksListContract.Action
import com.emikhalets.notes.presentation.screens.tasks.TasksListContract.Effect
import com.emikhalets.notes.presentation.screens.tasks.TasksListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val tasksUseCase: TasksUseCase,
) : BaseViewModel<Action, Effect, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.GetTask -> getTasks()
            is Action.DeleteTask -> deleteTask(action.task)
            is Action.DeleteTaskDialog -> setEffect { Effect.DeleteTaskDialog(action.task) }
            is Action.CompleteTask -> updateTask(action.task, action.complete)
        }
    }

    private fun getTasks() {
        launchScope {
            tasksUseCase.getAllFlow()
                .onSuccess { flow -> setAllTasksState(flow) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun deleteTask(entity: TaskEntity?) {
        entity ?: return
        launchScope {
            tasksUseCase.delete(entity)
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun updateTask(entity: TaskEntity?, complete: Boolean) {
        launchScope {
            entity ?: return@launchScope
            val result = if (entity.id == 0L) {
                tasksUseCase.insert(entity.copy(isCompleted = complete))
            } else {
                tasksUseCase.update(entity.copy(isCompleted = complete))
            }
            result.onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private suspend fun setAllTasksState(flow: Flow<List<TaskEntity>>) {
        flow.collectLatest { list ->
            val tasks = list.filter { !it.isCompleted }
            val checked = list.filter { it.isCompleted }
            setState { it.copy(isLoading = false, tasksList = tasks, checkedList = checked) }
        }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        setState { it.copy(isLoading = false) }
        setEffect { Effect.Error(message) }
    }
}

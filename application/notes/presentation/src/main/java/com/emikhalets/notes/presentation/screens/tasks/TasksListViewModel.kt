package com.emikhalets.notes.presentation.screens.tasks

import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.domain.usecase.SubtasksUseCase
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
    private val subtasksUseCase: SubtasksUseCase,
) : BaseViewModel<Action, Effect, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.GetTask -> getTasks()
            is Action.DeleteTask -> deleteTask(action.task)
            is Action.DeleteTaskDialog -> setEffect { Effect.DeleteTaskDialog(action.task) }
            is Action.CompleteTask -> updateTask(action.task, action.complete)
            is Action.CompleteSubtask -> updateSubtask(action.task, action.complete)
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

    private fun updateSubtask(entity: SubtaskEntity?, complete: Boolean) {
        launchScope {
            entity ?: return@launchScope
            subtasksUseCase.update(entity.copy(isCompleted = complete))
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private suspend fun setAllTasksState(flow: Flow<List<TaskEntity>>) {
        flow.collectLatest { list ->
            val tasks = list
                .filter { !it.isCompleted }
                .map { entity ->
                    entity.copy(subtasks = entity.subtasks.sortedBy { it.isCompleted })
                }
            val checked = list.filter { it.isCompleted }
            setState { it.copy(isLoading = false, tasksList = tasks, checkedList = checked) }
        }
    }

    private fun handleFailure(code: Int, message: com.emikhalets.core.common.UiString?) {
        setState { it.copy(isLoading = false) }
        setEffect { Effect.Error(message) }
    }
}

package com.emikhalets.notes.presentation.screens.tasks

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.domain.usecase.SubtasksUseCase
import com.emikhalets.notes.domain.usecase.TasksUseCase
import com.emikhalets.notes.presentation.screens.tasks.TasksListContract.Action
import com.emikhalets.notes.presentation.screens.tasks.TasksListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val tasksUseCase: TasksUseCase,
    private val subtasksUseCase: SubtasksUseCase,
) : BaseViewModel<Action, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        logd(TAG, "User event: ${action.javaClass.simpleName}")
        when (action) {
            Action.DropError -> setState { it.copy(error = null) }
            Action.SwitchCheckedExpand -> switchCheckedTasksExpand()
            Action.GetTask -> getTasks()
            is Action.SetEditingTask -> setState { it.copy(editingTask = action.task) }
            is Action.CheckTask -> updateTask(action.task, action.check)
            is Action.CheckSubtask -> updateSubtask(action.subtask, action.check)
        }
    }

    private fun switchCheckedTasksExpand() {
        setState { it.copy(isCheckedTasksExpanded = !currentState.isCheckedTasksExpanded) }
    }

    private fun getTasks() {
        logd(TAG, "Get tasks")
        launchScope {
            tasksUseCase.getAllFlow()
                .onSuccess { flow -> setAllTasksState(flow) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun updateTask(entity: TaskEntity?, complete: Boolean) {
        logd(TAG, "Update task: entity = $entity, complete = $complete")
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
        logd(TAG, "Update subtask: entity = $entity, complete = $complete")
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
            logd(TAG, "Collecting tasks:\nlist = $tasks\ncompleted = $checked")
            setState { it.copy(isLoading = false, tasksList = tasks, checkedList = checked) }
        }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        logd(TAG, "Handling error: code = $code")
        setState { it.copy(isLoading = false, error = message) }
    }

    companion object {

        private const val TAG = "TasksListVM"
    }
}

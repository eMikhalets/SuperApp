package com.emikhalets.feature.tasks.presentation

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.MviViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.feature.tasks.domain.AddTaskUseCase
import com.emikhalets.feature.tasks.domain.DeleteTaskUseCase
import com.emikhalets.feature.tasks.domain.GetTasksUseCase
import com.emikhalets.feature.tasks.domain.TaskModel
import com.emikhalets.feature.tasks.presentation.TasksListContract.Action
import com.emikhalets.feature.tasks.presentation.TasksListContract.Effect
import com.emikhalets.feature.tasks.presentation.TasksListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : MviViewModel<Action, Effect, State>() {

    init {
        logd(TAG, "Get tasks")
        launchScope {
            getTasksUseCase()
                .catch { handleFailure(it) }
                .collectLatest { setTasksList(it) }
        }
    }

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.DropTaskDialog -> dropTaskDialog()
            Action.DropError -> setState { it.copy(error = null) }
            Action.SwitchCheckedExpand -> switchCheckedTasksExpand()
            is Action.TaskClicked -> setEditTaskState(action.task)
            is Action.CheckTask -> updateTask(action.task, action.check)
            is Action.SaveTask -> saveTask(action.task)
        }
    }

    override fun handleError(message: String?) {
        setState { it.copy(error = UiString.create(message)) }
    }

    private fun dropTaskDialog() {
        setState { it.copy(showTaskDialog = false) }
    }

    private fun switchCheckedTasksExpand() {
        setState { it.copy(isCheckedTasksExpanded = !currentState.isCheckedTasksExpanded) }
    }

    private fun setEditTaskState(task: TaskModel) {
        setState { it.copy(editingTask = task, showTaskDialog = true) }
    }

    private fun saveTask(task: TaskModel) {
        logd(TAG, "Save task: $task")
        launchScope {
            dropTaskDialog()
            addTaskUseCase(task)
        }
    }

    private fun updateTask(task: TaskModel, complete: Boolean) {
        logd(TAG, "Update task: task = $task, complete = $complete")
        launchScope {
            addTaskUseCase(task.copy(completed = complete))
        }
    }

    private fun setTasksList(list: List<TaskModel>) {
        val tasks = list.filter { !it.completed }
            .map { model -> model.copy(subtasks = model.subtasks.sortedBy { it.completed }) }
        val checked = list.filter { it.completed }
        logd(TAG, "Collecting tasks:\nlist = $tasks\ncompleted = $checked")
        setState { it.copy(isLoading = false, tasksList = tasks, checkedList = checked) }
    }

    private fun handleFailure(throwable: Throwable) {
        logd(TAG, "Handling error: throwable = $throwable")
        setState { it.copy(isLoading = false, error = UiString.create(throwable)) }
    }

    companion object {

        private const val TAG = "TasksListVM"
    }
}

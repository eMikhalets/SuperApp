package com.emikhalets.feature.tasks.presentation

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.feature.tasks.data.Repository
import com.emikhalets.feature.tasks.data.TaskModel
import com.emikhalets.feature.tasks.presentation.TasksListContract.Action
import com.emikhalets.feature.tasks.presentation.TasksListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel<Action, State>() {

    init {
        logd(TAG, "Get tasks")
        launchScope {
            repository.getTasks()
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
        dropTaskDialog()
        launchScope {
            if (task.id == 0L) repository.insertTask(task)
            else repository.updateTask(task)
        }
    }

    private fun updateTask(task: TaskModel, complete: Boolean) {
        logd(TAG, "Update task: task = $task, complete = $complete")
        launchScope {
            repository.updateTask(task.copy(completed = complete))
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

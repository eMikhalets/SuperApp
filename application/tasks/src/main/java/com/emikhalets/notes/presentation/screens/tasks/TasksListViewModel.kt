package com.emikhalets.notes.presentation.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.simplenotes.domain.use_cases.tasks.DeleteTaskUseCase
import com.emikhalets.simplenotes.domain.use_cases.tasks.GetAllTasksUseCase
import com.emikhalets.simplenotes.domain.use_cases.tasks.InsertTaskUseCase
import com.emikhalets.simplenotes.domain.use_cases.tasks.UpdateSubtaskUseCase
import com.emikhalets.simplenotes.domain.use_cases.tasks.UpdateTaskUseCase
import com.emikhalets.simplenotes.domain.use_cases.tasks.UpdateTasksListUseCase
import com.emikhalets.simplenotes.utils.AppDataStore
import com.emikhalets.simplenotes.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val updateTasksListUseCase: UpdateTasksListUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateSubtaskUseCase: UpdateSubtaskUseCase,
    val dataStore: AppDataStore,
) : ViewModel() {

    private val _state = MutableStateFlow(TasksListState())
    val state get() = _state.asStateFlow()

    fun resetError() = _state.update { it.copy(error = null) }

    fun getAllTasks() {
        viewModelScope.launch {
            getAllTasksUseCase.invoke()
                .onSuccess { flow -> setAllTasksState(flow) }
                .onFailure { throwable -> handleFailure(throwable) }
        }
    }

    private suspend fun setAllTasksState(flow: Flow<List<com.emikhalets.notes.domain.entity.TaskEntity>>) {
        flow.collectLatest { list ->
            val tasks = list
                .filter { !it.checked }
                .map { com.emikhalets.notes.domain.entity.TasksListWrapper(it) }
            val checked = list
                .filter { it.checked }
                .map { com.emikhalets.notes.domain.entity.TasksListWrapper(it) }
            _state.update { it.copy(tasksList = tasks, checkedList = checked) }
        }
    }

    fun updateTask(entity: com.emikhalets.notes.domain.entity.TaskEntity, newChecked: Boolean) {
        viewModelScope.launch {
            val newEntity = entity.copy(checked = newChecked)
            updateTaskUseCase.invoke(newEntity).onFailure { throwable -> handleFailure(throwable) }
        }
    }

    fun updateSubtask(task: com.emikhalets.notes.domain.entity.TaskEntity, subtask: com.emikhalets.notes.domain.entity.SubtaskEntity, checked: Boolean) {
        viewModelScope.launch {
            val newEntity = task.subtasks.find { it.id == subtask.id }
                ?.copy(checked = checked)
                ?: return@launch
            updateSubtaskUseCase.invoke(newEntity)
                .onFailure { throwable -> handleFailure(throwable) }
        }
    }

    fun updateTask(entity: com.emikhalets.notes.domain.entity.TaskEntity) {
        viewModelScope.launch {
            val result = if (entity.id == 0L) {
                insertTaskUseCase.invoke(entity)
            } else {
                updateTaskUseCase.invoke(entity)
            }
            result.onFailure { throwable -> handleFailure(throwable) }
        }
    }

    fun deleteTask(entity: com.emikhalets.notes.domain.entity.TaskEntity) {
        viewModelScope.launch {
            deleteTaskUseCase.invoke(entity).onFailure { throwable -> handleFailure(throwable) }
        }
    }

    private fun handleFailure(throwable: Throwable) {
        _state.update { it.copy(error = UiString.create(throwable.message)) }
    }
}

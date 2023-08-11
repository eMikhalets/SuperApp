package com.emikhalets.fitness.presentation.stages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.common.UiString
import com.emikhalets.fitness.domain.entity.WorkoutDoneType
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.WorkoutType
import com.emikhalets.fitness.domain.use_case.GetWorkoutsUseCase
import com.emikhalets.fitness.domain.use_case.UpdateWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StagesViewModel @Inject constructor(
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val updateWorkoutUseCase: UpdateWorkoutUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<StagesState> = MutableStateFlow(StagesState())
    val state get(): StateFlow<StagesState> = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.copy(error = null) }
    }

    fun getWorkout(stageType: WorkoutType) {
        viewModelScope.launch {
            getWorkoutsUseCase(stageType)
                .onSuccess { setWorkoutState(it) }
                .onFailure { setErrorState(UiString.create()) }
        }
    }

    fun updateWorkout(entity: WorkoutEntity) {
        viewModelScope.launch {
            updateWorkoutUseCase(entity)
                .onFailure { setErrorState(UiString.create()) }
        }
    }

    private suspend fun setWorkoutState(flow: Flow<List<WorkoutEntity>>) {
        flow.collectLatest { list ->
            val isNeedWorkout = list.any { it.doneType != WorkoutDoneType.DONE }
            _state.update { _state.value.copy(workoutList = list, isNeedWorkout = isNeedWorkout) }
        }
    }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.copy(error = message) }
    }
}
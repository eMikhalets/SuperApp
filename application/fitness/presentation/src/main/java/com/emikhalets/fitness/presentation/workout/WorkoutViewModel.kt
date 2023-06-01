package com.emikhalets.fitness.presentation.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.common.UiString
import com.emikhalets.fitness.domain.FitnessConst
import com.emikhalets.fitness.domain.entity.WorkoutDoneType
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.WorkoutType
import com.emikhalets.fitness.domain.use_case.GetWorkoutsUseCase
import com.emikhalets.fitness.domain.use_case.UpdateWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val updateWorkoutUseCase: UpdateWorkoutUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<WorkoutState> = MutableStateFlow(WorkoutState())
    val state get(): StateFlow<WorkoutState> = _state.asStateFlow()

    private var currentRepIndex: Int = 0

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

    fun startTimer() {
        val reps = _state.value.workout?.reps ?: return
        if (reps.size - 1 < currentRepIndex) {
            updateOnDone()
            return
        }
        viewModelScope.launch {
            _state.update { _state.value.copy(isTimerActive = true) }
            while (_state.value.timerValue > 0) {
                delay(1000)
                _state.update { _state.value.copy(timerValue = _state.value.timerValue - 1) }
            }
            currentRepIndex++
            if (reps.size - 1 >= currentRepIndex) {
                _state.update {
                    _state.value.copy(
                        isTimerActive = false,
                        timerValue = FitnessConst.WORKOUT_TIMER,
                        currentRep = _state.value.workout?.reps?.get(currentRepIndex) ?: 0,
                    )
                }
            } else {
                updateOnDone()
                _state.update {
                    _state.value.copy(
                        isTimerActive = false,
                        timerValue = FitnessConst.WORKOUT_TIMER
                    )
                }
            }
        }
    }

    fun updateOnRepeat(entity: WorkoutEntity) {
        viewModelScope.launch {
            updateWorkoutUseCase(entity.copy(doneType = WorkoutDoneType.REPEAT))
                .onSuccess { _state.update { _state.value.copy(isCompleted = true) } }
                .onFailure { setErrorState(UiString.create()) }
        }
    }

    private fun updateOnDone() {
        val entity = _state.value.workout ?: return
        viewModelScope.launch {
            updateWorkoutUseCase(entity.copy(doneType = WorkoutDoneType.DONE))
                .onSuccess { _state.update { _state.value.copy(isCompleted = true) } }
                .onFailure { setErrorState(UiString.create()) }
        }
    }

    private suspend fun setWorkoutState(flow: Flow<List<WorkoutEntity>>) {
        flow.collectLatest { list ->
            try {
                val index = list.indexOfFirst { it.doneType != WorkoutDoneType.DONE }
                _state.update {
                    _state.value.copy(
                        workout = list[index],
                        currentRep = list[index].reps[currentRepIndex]
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.copy(error = message) }
    }
}
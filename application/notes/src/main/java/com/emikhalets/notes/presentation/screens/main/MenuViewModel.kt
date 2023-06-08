package com.emikhalets.notes.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.common.UiString
import com.emikhalets.fitness.domain.use_case.GetWorkoutsUseCase
import com.emikhalets.fitness.domain.use_case.InitWorkoutsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val initWorkoutsUseCase: InitWorkoutsUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<MenuState> = MutableStateFlow(MenuState())
    val state get(): StateFlow<MenuState> = _state.asStateFlow()

    fun dropError() = _state.update { _state.value.copy(error = null) }

    fun initWorkouts() {
        viewModelScope.launch {
            getWorkoutsUseCase()
                .onSuccess { setStagesState(it) }
                .onFailure { setErrorState(UiString.create()) }
        }
    }

    private fun setStagesState(stages: List<Int>) {
        if (stages.isEmpty()) {
            addWorkouts()
        } else {
            setWorkoutInitializedSavedState()
        }
    }

    private fun addWorkouts() {
        viewModelScope.launch {
            initWorkoutsUseCase()
                .onSuccess { setWorkoutInitializedSavedState() }
                .onFailure { setErrorState(UiString.create()) }
        }
    }

    private fun setWorkoutInitializedSavedState() {
        _state.update { _state.value.copy(workoutInitialized = true) }
    }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.copy(error = message) }
    }
}

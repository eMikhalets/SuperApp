package com.emikhalets.feature.workout.presentation.program_add

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.MviViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.feature.workout.domain.AddProgramUseCase
import com.emikhalets.feature.workout.domain.model.ProgramModel
import com.emikhalets.feature.workout.domain.model.ProgramType
import com.emikhalets.feature.workout.domain.model.WorkoutModel
import com.emikhalets.feature.workout.presentation.program_add.ProgramAddContract.Action
import com.emikhalets.feature.workout.presentation.program_add.ProgramAddContract.Effect
import com.emikhalets.feature.workout.presentation.program_add.ProgramAddContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProgramAddViewModel @Inject constructor(
    private val addProgramUseCase: AddProgramUseCase,
) : MviViewModel<Action, Effect, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.DropError -> dropErrorState()
            Action.AddProgram -> addProgram()
            Action.AddWorkout -> addWorkout()
            is Action.NameChanged -> setName(action.name)
            is Action.TypeChanged -> setType(action.type)
            is Action.WorkoutChanged -> setWorkout(action.type)
        }
    }

    override fun handleError(message: String?) {
        setState { it.copy(error = UiString.create(message)) }
    }

    private fun dropErrorState() {
        setState { it.copy(error = null) }
    }

    private fun setName(name: String) {
        setState { it.copy(name = name) }
    }

    private fun setType(type: ProgramType) {
        setState { it.copy(type = type) }
    }

    private fun setWorkout(type: ProgramType) {
        setState { it.copy(type = type) }
    }

    private fun addWorkout() {
        val newList = currentState.workouts
            .toMutableList()
            .also { it.add(WorkoutModel()) }
        setState { it.copy(workouts = newList) }
    }

    private fun addProgram() {
        launchScope {
            val model = ProgramModel(currentState.name, currentState.type, currentState.workouts)
            addProgramUseCase(model)
            setEffect { Effect.ProgramAdded }
        }
    }
}

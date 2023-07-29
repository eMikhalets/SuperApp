package com.emikhalets.feature.workout.presentation.program_add

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.UndefinedIndex
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.feature.workout.domain.model.ProgramType
import com.emikhalets.feature.workout.domain.model.WorkoutModel

object ProgramAddContract {

    @Immutable
    sealed class Action : UiAction {

        object DropError : Action()
        object AddProgram : Action()
        class NameChanged(val name: String) : Action()
        class TypeChanged(val type: ProgramType) : Action()
        class ChangeWorkouts(val index: Int = UndefinedIndex) : Action()
        class ChangeExercises(val parentIndex: Int, val index: Int = UndefinedIndex) : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {

        object ProgramAdded : Effect()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val isProgramSaved: Boolean = false,
        val name: String = "",
        val type: ProgramType = ProgramType.Dynamic,
        val workouts: List<WorkoutModel> = emptyList(),
        val error: UiString? = null,
    ) : UiState
}

package com.emikhalets.fitness.presentation.program_edit

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.entity.WorkoutEntity

object ProgramEditContract {

    @Immutable
    sealed class Action : UiAction {

        class SaveProgram(val name: String, val workouts: List<WorkoutEntity>) : Action()
        class GetProgram(val id: Long) : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {

        object ProgramSaved : Effect()
        class Error(val message: UiString?) : Effect()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val program: ProgramEntity? = null,
    ) : UiState
}

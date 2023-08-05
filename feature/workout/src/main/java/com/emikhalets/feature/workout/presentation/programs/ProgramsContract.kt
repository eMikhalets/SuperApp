package com.emikhalets.feature.workout.presentation.programs

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.feature.workout.domain.model.ProgramModel

object ProgramsContract {

    @Immutable
    sealed class Action : UiAction {

        object DropError : Action()
        object AddProgramClick : Action()
        class ProgramClick(val id: Long) : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {

        class NavigateToProgram(val id: Long) : Effect()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val programs: List<ProgramModel> = emptyList(),
        val error: UiString? = null,
    ) : UiState
}

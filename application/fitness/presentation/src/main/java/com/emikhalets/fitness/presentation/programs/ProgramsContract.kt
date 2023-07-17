package com.emikhalets.fitness.presentation.programs

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.fitness.domain.entity.ProgramEntity

object ProgramsContract {

    @Immutable
    sealed class Action : UiAction {

        object DropError : Action()
        object GetPrograms : Action()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val programs: List<ProgramEntity> = emptyList(),
        val error: UiString? = null,
    ) : UiState
}

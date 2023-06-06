package com.emikhalets.fitness.presentation.screens.main

import androidx.compose.runtime.Immutable
import com.emikhalets.ui.UiAction
import com.emikhalets.ui.UiEffect
import com.emikhalets.ui.UiState
import com.emikhalets.ui.UiString

object MainContract {

    @Immutable
    data class State(
        val isLoading: Boolean = true,
        val isAlarmsCreated: Boolean = false,
    ) : UiState

    @Immutable
    sealed class Action : UiAction {
        object CreateDefaultAlarms : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {
        class ErrorDialog(val message: UiString?) : Effect()
    }
}

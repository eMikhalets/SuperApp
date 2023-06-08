package com.emikhalets.notes.presentation.screens.main

import androidx.compose.runtime.Immutable
import com.emikhalets.core.ui.UiString

object EventsMainContract {

    @Immutable
    data class State(
        val isLoading: Boolean = true,
        val savedEvents: List<EventEntity> = emptyList(),
        val events: List<EventEntity> = emptyList(),
        val searchQuery: String = "",
    ) : UiState

    @Immutable
    sealed class Action : UiAction {
        object GetEvents : Action()
        class SearchEvents(val query: String) : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {
        class ErrorDialog(val message: UiString?) : Effect()
    }
}

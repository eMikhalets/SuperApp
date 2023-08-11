package com.emikhalets.fitness.presentation.screens.events_list

import androidx.compose.runtime.Immutable
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.ui.UiAction
import com.emikhalets.ui.UiEffect
import com.emikhalets.ui.UiState
import com.emikhalets.ui.UiString

object EventsListContract {

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

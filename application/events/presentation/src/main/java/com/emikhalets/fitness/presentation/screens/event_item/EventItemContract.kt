package com.emikhalets.fitness.presentation.screens.event_item

import androidx.compose.runtime.Immutable
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.ui.UiAction
import com.emikhalets.ui.UiEffect
import com.emikhalets.ui.UiState
import com.emikhalets.ui.UiString

object EventItemContract {

    @Immutable
    data class State(
        val isLoading: Boolean = true,
        val event: EventEntity? = null,
        val isDeleted: Boolean = false,
    ) : UiState

    @Immutable
    sealed class Action : UiAction {
        class GetEvent(val id: Long?) : Action()
        object DeleteEvent : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {
        class ErrorDialog(val message: UiString?) : Effect()
    }
}

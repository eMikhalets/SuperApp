package com.emikhalets.simpleevents.presentation.screens.event_item

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.ui.UiState

data class EventItemState(
    val event: EventEntity? = null,
    val loading: Boolean = false,
    val deleted: Boolean = false,
    val error: UiString? = null,
) : UiState

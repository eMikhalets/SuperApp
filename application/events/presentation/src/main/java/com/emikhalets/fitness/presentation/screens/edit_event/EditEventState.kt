package com.emikhalets.simpleevents.presentation.screens.edit_event

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.utils.AppState

data class EditEventState(
    val event: EventEntity? = null,
    val loading: Boolean = false,
    val updated: Boolean = false,
    val error: UiString? = null,
) : AppState

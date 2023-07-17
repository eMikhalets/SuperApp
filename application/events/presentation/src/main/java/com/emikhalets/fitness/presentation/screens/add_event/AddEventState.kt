package com.emikhalets.simpleevents.presentation.screens.add_event

import com.emikhalets.simpleevents.utils.AppState

data class AddEventState(
    val savedId: Long = 0,
    val loading: Boolean = false,
    val error: UiString? = null,
) : AppState

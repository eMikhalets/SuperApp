package com.emikhalets.simpleevents.presentation.screens.events_calendar

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.utils.AppState

data class EventsCalendarState(
    val eventsList: List<EventEntity> = emptyList(),
    val loading: Boolean = false,
    val error: UiString? = null,
) : AppState

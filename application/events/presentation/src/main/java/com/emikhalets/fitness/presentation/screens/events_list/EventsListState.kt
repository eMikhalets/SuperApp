package com.emikhalets.simpleevents.presentation.screens.events_list

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.utils.AppState

data class EventsListState(
    val eventsMap: Map<Long, List<EventEntity>> = emptyMap(),
    val loading: Boolean = false,
    val error: UiString? = null,
) : AppState

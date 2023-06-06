package com.emikhalets.simpleevents.presentation.screens.events_list

import com.emikhalets.ui.UiAction

sealed class EventsListAction : UiAction {
    object AcceptError : EventsListAction()
    object GetEvents : EventsListAction()
    class SearchEvents(val query: String) : EventsListAction()
}

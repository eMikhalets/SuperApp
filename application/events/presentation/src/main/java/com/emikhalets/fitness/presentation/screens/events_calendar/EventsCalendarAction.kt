package com.emikhalets.simpleevents.presentation.screens.events_calendar

import com.emikhalets.ui.UiAction

sealed class EventsCalendarAction : UiAction {
    object AcceptError : EventsCalendarAction()
    object GetEvents : EventsCalendarAction()
}

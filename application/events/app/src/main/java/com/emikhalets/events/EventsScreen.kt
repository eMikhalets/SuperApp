package com.emikhalets.events

enum class EventsScreen(val route: String) {

    Main("events_app_main"),
    EventsList("events_app_events_list"),
    EventsCalendar("events_calendar"),
    Groups("groups"),
    Settings("settings"),
    GroupItem("group_item"),
    GroupEdit("group_edit"),
    Event("event"),
    AddEvent("add_event"),
    EditEvent("edit_event");
}

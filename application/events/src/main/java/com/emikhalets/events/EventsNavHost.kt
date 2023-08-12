package com.emikhalets.events

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.fitness.presentation.screens.main.MainScreen
import com.emikhalets.fitness.presentation.stages.StagesScreen
import com.emikhalets.simpleevents.presentation.screens.add_event.AddEventScreen
import com.emikhalets.simpleevents.presentation.screens.edit_event.EditEventScreen
import com.emikhalets.simpleevents.presentation.screens.event_item.EventItemScreen
import com.emikhalets.simpleevents.presentation.screens.events_calendar.EventsCalendarScreen
import com.emikhalets.simpleevents.presentation.screens.events_calendar.EventsCalendarViewModel
import com.emikhalets.fitness.presentation.screens.events_list.EventsListScreen
import com.emikhalets.simpleevents.presentation.screens.group_edit.GroupEditScreen
import com.emikhalets.simpleevents.presentation.screens.group_edit.GroupEditViewModel
import com.emikhalets.simpleevents.presentation.screens.group_item.GroupItemScreen
import com.emikhalets.simpleevents.presentation.screens.group_item.GroupItemViewModel
import com.emikhalets.simpleevents.presentation.screens.groups.GroupsViewModel
import com.emikhalets.simpleevents.presentation.screens.settings.SettingsScreen
import com.emikhalets.simpleevents.presentation.screens.settings.SettingsViewModel

private const val ARGS_EVENT_ID = "args_event_id"
private const val ARGS_GROUP_ID = "args_group_id"

fun NavController.navigateToAppEvents(navOptions: NavOptions? = null) {
    this.navigate(EventsScreen.Main.route, navOptions)
}

fun NavGraphBuilder.applicationEvents(navController: NavHostController) {

    composable(EventsScreen.Main.route) {
        MainScreen(
            navigateToEventsList = { navController.navigate(EventsScreen.EventsList.route) },
            navigateBack = { navController.popBackStack() },
            viewModel = hiltViewModel()
        )
    }

    composable(EventsScreen.EventsList.route) {
        EventsListScreen(
            navigateToEvent = { id -> },
            navigateBack = { navController.popBackStack(EventsScreen.Main.route, true) },
            viewModel = hiltViewModel()
        )
    }

    composable(
        route = "${FitnessScreen.Stages.route}/{$ARGS_TYPE}",
        arguments = listOf(navArgument(ARGS_TYPE) { type = NavType.StringType })
    ) {
        it.arguments?.getString(ARGS_TYPE)?.let { argType ->
            StagesScreen(
                stageType = WorkoutType.valueOf(argType),
                navigateBack = { navController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }
    }

    composable(AppScreen.EventsCalendar.route) {
        val viewModel: EventsCalendarViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        EventsCalendarScreen(
            state = state,
            onAction = viewModel::setAction,
            onMonthClick = { monthNumber ->
                navController.navigate("${AppScreen.Event.route}/$monthNumber")
            }
        )
    }

    composable(AppScreen.Groups.route) {
        val viewModel: GroupsViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        GroupsScreen(
            state = state,
            onAction = viewModel::setAction,
            onGroupClick = {
                navController.navigate("${AppScreen.GroupItem.route}/$it")
            },
            onAddGroupClick = {
                navController.navigate("${AppScreen.GroupEdit.route}/${null}")
            }
        )
    }

    composable(AppScreen.Settings.route) {
        val viewModel: SettingsViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        SettingsScreen(
            state = state,
            onAction = viewModel::setAction,
        )
    }

    composable(
        route = "${AppScreen.GroupItem.route}/{${com.emikhalets.simpleevents.presentation.navigation.ARGS_GROUP_ID}}",
        arguments = listOf(navArgument(com.emikhalets.simpleevents.presentation.navigation.ARGS_GROUP_ID) {
            type = NavType.LongType
        })
    ) {
        val viewModel: GroupItemViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        GroupItemScreen(
            groupId = it.arguments?.getLong(com.emikhalets.simpleevents.presentation.navigation.ARGS_GROUP_ID),
            state = state,
            onAction = viewModel::setAction,
        )
    }

    composable(
        route = "${AppScreen.GroupEdit.route}/{${com.emikhalets.simpleevents.presentation.navigation.ARGS_GROUP_ID}}",
        arguments = listOf(navArgument(com.emikhalets.simpleevents.presentation.navigation.ARGS_GROUP_ID) {
            type = NavType.LongType
        })
    ) {
        val viewModel: GroupEditViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        GroupEditScreen(
            groupId = it.arguments?.getLong(com.emikhalets.simpleevents.presentation.navigation.ARGS_GROUP_ID),
            state = state,
            onAction = viewModel::setAction,
            onGroupSaved = { navController.popBackStack() }
        )
    }

    composable(AppScreen.AddEvent.route) {
        AddEventScreen(
            viewModel = hiltViewModel(),
            onEventAdded = { eventId ->
                navController.navigate("${AppScreen.Event.route}/$eventId") {
                    popUpTo(AppScreen.EventsList.route)
                }
            }
        )
    }

    composable(
        route = "${AppScreen.Event.route}/{${com.emikhalets.simpleevents.presentation.navigation.ARGS_EVENT_ID}}",
        arguments = listOf(navArgument(com.emikhalets.simpleevents.presentation.navigation.ARGS_EVENT_ID) {
            type = NavType.LongType
        })
    ) { backStackEntry ->
        backStackEntry.arguments?.getLong(com.emikhalets.simpleevents.presentation.navigation.ARGS_EVENT_ID)
            ?.let { id ->
                EventItemScreen(
                    eventId = id,
                    viewModel = hiltViewModel(),
                    onEventDeleted = {
                        navController.popBackStack()
                    },
                    onEventEditClick = { eventId ->
                        navController.navigate("${AppScreen.EditEvent.route}/$eventId")
                    }
                )
            }
    }

    composable(
        route = "${AppScreen.EditEvent.route}/{${com.emikhalets.simpleevents.presentation.navigation.ARGS_EVENT_ID}}",
        arguments = listOf(navArgument(com.emikhalets.simpleevents.presentation.navigation.ARGS_EVENT_ID) {
            type = NavType.LongType
        })
    ) { backStackEntry ->
        backStackEntry.arguments?.getLong(com.emikhalets.simpleevents.presentation.navigation.ARGS_EVENT_ID)
            ?.let { id ->
                EditEventScreen(
                    eventId = id,
                    viewModel = hiltViewModel()
                )
            }
    }
}

package com.emikhalets.notes.app

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.emikhalets.core.common.AppCode
import com.emikhalets.core.navigation.ARGS
import com.emikhalets.core.navigation.AppScreen
import com.emikhalets.notes.presentation.screens.note_item.NoteItemScreen
import com.emikhalets.notes.presentation.screens.notes.NotesListScreen
import com.emikhalets.notes.presentation.screens.settings.NotesSettingsScreen
import com.emikhalets.notes.presentation.screens.tasks.TasksListScreen

private val bottomBarItems: List<AppScreen> = listOf(
    AppNotesScreen.TasksList, AppNotesScreen.NotesList, AppNotesScreen.Settings
)

fun NavGraphBuilder.applicationNotes(
    graphRoute: String,
    navController: NavHostController,
    bottomBarList: (List<AppScreen>) -> Unit,
) {
    navigation(AppNotesScreen.TasksList.route, graphRoute) {
        composable(AppNotesScreen.TasksList.route) {
            bottomBarList(bottomBarItems)
            TasksListScreen(
                navigateBack = { navController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }
        composable(AppNotesScreen.NotesList.route) {
            NotesListScreen(
                navigateToNote = { id ->
                    navController.navigate("${AppNotesScreen.NoteItem.route}/$id")
                },
                navigateBack = {
                    navController.popBackStack()
                },
                viewModel = hiltViewModel()
            )
        }
        composable(
            "${AppNotesScreen.NoteItem.route}/{${ARGS.NOTE_ID}}",
            listOf(navArgument(ARGS.NOTE_ID) { type = NavType.LongType })
        ) {
            NoteItemScreen(
                navigateBack = {
                    navController.popBackStack(AppNotesScreen.NotesList.route, false)
                },
                viewModel = hiltViewModel(),
                noteId = it.arguments?.getLong(ARGS.NOTE_ID) ?: AppCode.NO_ID
            )
        }
        composable(AppNotesScreen.Settings.route) {
            NotesSettingsScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}

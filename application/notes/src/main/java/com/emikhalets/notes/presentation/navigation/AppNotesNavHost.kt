package com.emikhalets.notes.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.emikhalets.core.ARGS
import com.emikhalets.core.AppScreen
import com.emikhalets.core.common.AppCode
import com.emikhalets.notes.presentation.screens.note_item.NoteItemScreen
import com.emikhalets.notes.presentation.screens.notes.NotesListScreen
import com.emikhalets.notes.presentation.screens.settings.NotesSettingsScreen

fun NavGraphBuilder.applicationNotes(
    graphRoute: String,
    navController: NavHostController,
    bottomBarList: (List<AppScreen>) -> Unit,
) {
    navigation(AppNotesScreen.NotesList.route, graphRoute) {
        composable(AppNotesScreen.NotesList.route) {
            bottomBarList(listOf(AppNotesScreen.NotesList, AppNotesScreen.Settings))
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

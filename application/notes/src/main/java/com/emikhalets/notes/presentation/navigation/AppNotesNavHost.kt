package com.emikhalets.notes.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.core.ARGS
import com.emikhalets.core.AppBottomBarItem
import com.emikhalets.notes.presentation.screens.note_item.NoteItemScreen
import com.emikhalets.notes.presentation.screens.notes.NotesListScreen
import com.emikhalets.notes.presentation.screens.settings.NotesSettingsScreen

private val noteItemRoute: String = "${AppNotesScreen.NoteItem.route}/{${ARGS.NOTE_ID}}"

fun NavController.navigateToAppNotes(navOptions: NavOptions? = null) {
    this.navigate(AppNotesScreen.NotesList.route, navOptions)
}

fun NavGraphBuilder.applicationNotes(
    navController: NavHostController,
    bottomBarList: (List<AppBottomBarItem>) -> Unit,
) {
    bottomBarList(listOf(AppNotesScreen.NotesList, AppNotesScreen.Settings))
    composable(AppNotesScreen.NotesList.route) {
        NotesListScreen(
            navigateToNote = { id ->
                navController.navigate("${AppNotesScreen.NoteItem.route}/$id")
            },
            navigateBack = {
                navController.popBackStack(
                    AppNotesScreen.NotesList.route,
                    true
                )
            },
            viewModel = hiltViewModel()
        )
    }
    composable(
        noteItemRoute,
        listOf(navArgument(ARGS.NOTE_ID) { type = NavType.LongType })
    ) {
        NoteItemScreen(
            navigateBack = {
                navController.popBackStack(
                    AppNotesScreen.NotesList.route,
                    true
                )
            },
            viewModel = hiltViewModel(),
            noteId = it.arguments?.getLong(ARGS.NOTE_ID)
        )
    }
    composable(AppNotesScreen.Settings.route) {
        NotesSettingsScreen(
            navigateBack = { navController.popBackStack() },
        )
    }
}

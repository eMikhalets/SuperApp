package com.emikhalets.notes.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.notes.presentation.screens.note_item.NoteItemScreen
import com.emikhalets.notes.presentation.screens.notes.NotesListScreen
import com.emikhalets.notes.presentation.screens.settings.NotesSettingsScreen
import com.emikhalets.notes.presentation.screens.tasks.TasksListScreen

private val bottomBarItems: List<AppBottomBarItem> = listOf(
    object : AppBottomBarItem {
        override val route: String = AppNotesDestination.Tasks
        override val icon: ImageVector = Icons.Rounded.Checklist
    },
    object : AppBottomBarItem {
        override val route: String = AppNotesDestination.Notes
        override val icon: ImageVector = Icons.Rounded.EditNote
    },
    object : AppBottomBarItem {
        override val route: String = AppNotesDestination.Settings
        override val icon: ImageVector = Icons.Rounded.Settings
    },
)

fun NavGraphBuilder.applicationNotes(
    navController: NavHostController,
    bottomBarList: (List<AppBottomBarItem>) -> Unit,
) {
    navigation(AppNotesDestination.Tasks, AppNotesDestination.NavGraph) {
        composable(AppNotesDestination.Tasks) {
            bottomBarList(bottomBarItems)
            TasksListScreen(
                navigateBack = { navController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }
        composable(AppNotesDestination.Notes) {
            NotesListScreen(
                navigateToNote = { id ->
                    navController.navigate(AppNotesDestination.noteWithArgs(id))
                },
                navigateBack = { navController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }
        composable(AppNotesDestination.NoteWithArgs, AppNotesDestination.noteArgsList) {
            NoteItemScreen(
                navigateBack = {
                    navController.popBackStack(AppNotesDestination.Notes, false)
                },
                viewModel = hiltViewModel(),
                noteId = AppNotesDestination.getNoteArgsId(it)
            )
        }
        composable(AppNotesDestination.Settings) {
            NotesSettingsScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}

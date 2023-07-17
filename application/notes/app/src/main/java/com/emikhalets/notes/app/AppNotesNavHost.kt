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
import com.emikhalets.core.common.logi
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.notes.presentation.screens.note_item.NoteItemScreen
import com.emikhalets.notes.presentation.screens.notes.NotesListScreen
import com.emikhalets.notes.presentation.screens.settings.SettingsScreen
import com.emikhalets.notes.presentation.screens.tasks.TasksListScreen

private const val TAG = "AppNotesGraph"

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
            logi(TAG, "Composable TasksListScreen")
            bottomBarList(bottomBarItems)
            TasksListScreen(
                navigateBack = {
                    logi(TAG, "Navigate back")
                    navController.popBackStack()
                },
                viewModel = hiltViewModel()
            )
        }
        composable(AppNotesDestination.Notes) {
            logi(TAG, "Composable NotesListScreen")
            bottomBarList(bottomBarItems)
            NotesListScreen(
                navigateToNote = { id ->
                    logi(TAG, "Navigate to note: id = $id")
                    navController.navigate(AppNotesDestination.noteWithArgs(id))
                },
                navigateBack = {
                    logi(TAG, "Navigate back")
                    navController.popBackStack()
                },
                viewModel = hiltViewModel()
            )
        }
        composable(AppNotesDestination.NoteWithArgs, AppNotesDestination.noteArgsList) {
            logi(TAG, "Composable NoteItemScreen")
            NoteItemScreen(
                navigateBack = {
                    logi(TAG, "Navigate back to notes list")
                    navController.popBackStack(AppNotesDestination.Notes, false)
                },
                viewModel = hiltViewModel(),
                noteId = AppNotesDestination.getNoteArgsId(it)
            )
        }
        composable(AppNotesDestination.Settings) {
            logi(TAG, "Composable SettingsScreen")
            SettingsScreen(
                navigateBack = {
                    logi(TAG, "Navigate back")
                    navController.popBackStack()
                }
            )
        }
    }
}

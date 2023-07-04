package com.emikhalets.fitness.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.emikhalets.core.common.logi
import com.emikhalets.core.navigation.AppBottomBarItem

private const val TAG = "AppFitnessGraph"

private val bottomBarItems: List<AppBottomBarItem> = listOf(
    object : AppBottomBarItem {
        override val route: String = AppFitnessDestination.Tasks
        override val icon: ImageVector = Icons.Rounded.Checklist
    },
    object : AppBottomBarItem {
        override val route: String = AppFitnessDestination.Notes
        override val icon: ImageVector = Icons.Rounded.EditNote
    },
    object : AppBottomBarItem {
        override val route: String = AppFitnessDestination.Settings
        override val icon: ImageVector = Icons.Rounded.Settings
    },
)

fun NavGraphBuilder.applicationFitness(
    navController: NavHostController,
    bottomBarList: (List<AppBottomBarItem>) -> Unit,
) {
    navigation(AppFitnessDestination.Tasks, AppFitnessDestination.NavGraph) {
        composable(AppFitnessDestination.Tasks) {
            logi(TAG, "Composable TasksListScreen")
            bottomBarList(bottomBarItems)
//            TasksListScreen(
//                navigateBack = {
//                    logi(TAG, "Navigate back")
//                    navController.popBackStack()
//                },
//                viewModel = hiltViewModel()
//            )
        }
        composable(AppFitnessDestination.Notes) {
            logi(TAG, "Composable NotesListScreen")
//            NotesListScreen(
//                navigateToNote = { id ->
//                    logi(TAG, "Navigate to note: id = $id")
//                    navController.navigate(AppFitnessDestination.noteWithArgs(id))
//                },
//                navigateBack = {
//                    logi(TAG, "Navigate back")
//                    navController.popBackStack()
//                },
//                viewModel = hiltViewModel()
//            )
        }
        composable(AppFitnessDestination.NoteWithArgs, AppFitnessDestination.noteArgsList) {
            logi(TAG, "Composable NoteItemScreen")
//            NoteItemScreen(
//                navigateBack = {
//                    logi(TAG, "Navigate back to notes list")
//                    navController.popBackStack(AppFitnessDestination.Notes, false)
//                },
//                viewModel = hiltViewModel(),
//                noteId = AppFitnessDestination.getNoteArgsId(it)
//            )
        }
        composable(AppFitnessDestination.Settings) {
            logi(TAG, "Composable SettingsScreen")
//            SettingsScreen(
//                navigateBack = {
//                    logi(TAG, "Navigate back")
//                    navController.popBackStack()
//                }
//            )
        }
    }
}

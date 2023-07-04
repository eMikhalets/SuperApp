package com.emikhalets.fitness.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.emikhalets.core.common.logi
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.fitness.presentation.program.ProgramScreen
import com.emikhalets.fitness.presentation.programs.ProgramsScreen

private const val TAG = "AppFitnessGraph"

fun NavGraphBuilder.applicationFitness(
    navController: NavHostController,
    bottomBarList: (List<AppBottomBarItem>) -> Unit,
) {
    navigation(AppFitnessDestination.Programs, AppFitnessDestination.NavGraph) {
        composable(AppFitnessDestination.Programs) {
            logi(TAG, "Composable ProgramsScreen")
            ProgramsScreen(
                navigateToProgram = { id ->
                    logi(TAG, "Navigate to program: id = $id")
                    navController.navigate(AppFitnessDestination.programWithArgs(id))
                },
                navigateBack = {
                    logi(TAG, "Navigate back")
                    navController.popBackStack()
                },
                viewModel = hiltViewModel()
            )
        }
        composable(AppFitnessDestination.ProgramWithArgs, AppFitnessDestination.programArgsList) {
            logi(TAG, "Composable NoteItemScreen")
            ProgramScreen(
                navigateBack = {
                    logi(TAG, "Navigate back")
                    navController.popBackStack()
                },
                viewModel = hiltViewModel(),
                programId = AppFitnessDestination.getProgramArgsId(it)
            )
        }
    }
}

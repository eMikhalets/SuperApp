package com.emikhalets.fitness

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.feature.workout.navigation.FeatureWorkoutDestination
import com.emikhalets.feature.workout.presentation.programs.ProgramsScreen

val applicationFitnessBottomBar: List<AppBottomBarItem> = listOf()

fun NavGraphBuilder.applicationFitnessGraph(navController: NavHostController) {
    navigation(
        startDestination = FeatureWorkoutDestination.Programs,
        route = AppFitnessDestination.NavGraph
    ) {
        composable(FeatureWorkoutDestination.Programs) {
            ProgramsScreen(
                navigateToProgram = { id ->
//                    navController.navigate(AppFitnessDestination.programEditWithArgs(id))
                },
                navigateBack = navController::popBackStack,
                viewModel = hiltViewModel()
            )
        }
//        composable(
//            AppFitnessDestination.ProgramWithArgs,
//            AppFitnessDestination.programArgsList
//        ) {
//            logi(TAG, "Composable ProgramScreen")
//            ProgramScreen(
//                navigateBack = {
//                    logi(TAG, "Navigate back")
//                    navController.popBackStack()
//                },
//                viewModel = hiltViewModel(),
//                programId = AppFitnessArgs.getProgramId(it)
//            )
//        }
//        composable(
//            AppFitnessDestination.ProgramEditWithArgs,
//            AppFitnessDestination.programEditArgsList
//        ) {
//            logi(TAG, "Composable ProgramEditScreen")
//            ProgramEditScreen(
//                navigateBack = {
//                    logi(TAG, "Navigate back")
//                    navController.popBackStack()
//                },
//                viewModel = hiltViewModel(),
//                programId = AppFitnessArgs.getProgramId(it)
//            )
//        }
    }
}
package com.emikhalets.feature.workout.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.emikhalets.feature.workout.presentation.program_add.ProgramAddScreen
import com.emikhalets.feature.workout.presentation.programs.ProgramsScreen

fun NavGraphBuilder.featureWorkoutComposable(navController: NavHostController) {
    composable(FeatureWorkoutDestination.Programs) {
        ProgramsScreen(
            navigateToProgram = { navController.navigate(FeatureWorkoutDestination.AddProgram) },
            navigateBack = navController::popBackStack,
            viewModel = hiltViewModel()
        )
    }
    composable(FeatureWorkoutDestination.AddProgram) {
        ProgramAddScreen(
            navigateBack = navController::popBackStack,
            viewModel = hiltViewModel()
        )
    }
}

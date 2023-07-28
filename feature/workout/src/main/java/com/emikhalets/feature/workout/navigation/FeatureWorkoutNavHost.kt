package com.emikhalets.feature.workout.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.emikhalets.feature.workout.presentation.programs.ProgramsScreen

fun NavGraphBuilder.featureWorkoutComposable(navController: NavHostController) {
    composable(FeatureWorkoutDestination.Programs) {
        ProgramsScreen(
            navigateToProgram = {},
            navigateBack = navController::popBackStack,
            viewModel = hiltViewModel()
        )
    }
}

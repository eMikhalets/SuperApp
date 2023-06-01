package com.emikhalets.fitness.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.fitness.domain.entity.WorkoutType
import com.emikhalets.fitness.presentation.menu.MenuScreen
import com.emikhalets.fitness.presentation.stages.StagesScreen
import com.emikhalets.fitness.presentation.workout.WorkoutScreen

private const val ARGS_TYPE = "args_workout_type"

fun NavController.navigateToAppFitness(navOptions: NavOptions? = null) {
    this.navigate(FitnessScreen.MainMenu.route, navOptions)
}

fun NavGraphBuilder.applicationFitness(navController: NavHostController) {

    composable(FitnessScreen.MainMenu.route) {
        MenuScreen(
            navigateToPress = {
                navController.navigate("${FitnessScreen.Stages.route}/${WorkoutType.PRESS}")
            },
            navigateToPullUp = {
                navController.navigate("${FitnessScreen.Stages.route}/${WorkoutType.PULL_UP}")
            },
            navigateToPushUp = {
                navController.navigate("${FitnessScreen.Stages.route}/${WorkoutType.PUSH_UP}")
            },
            navigateToSquat = {
                navController.navigate("${FitnessScreen.Stages.route}/${WorkoutType.SQUAT}")
            },
            navigateBack = { navController.popBackStack() },
            viewModel = hiltViewModel()
        )
    }

    composable(
        route = "${FitnessScreen.Stages.route}/{$ARGS_TYPE}",
        arguments = listOf(navArgument(ARGS_TYPE) { type = NavType.StringType })
    ) {
        it.arguments?.getString(ARGS_TYPE)?.let { argType ->
            StagesScreen(
                stageType = WorkoutType.valueOf(argType),
                navigateBack = { navController.popBackStack() },
                navigateToWorkout = { type ->
                    navController.navigate("${FitnessScreen.Workout.route}/$type")
                },
                viewModel = hiltViewModel()
            )
        }
    }

    composable(
        route = "${FitnessScreen.Workout.route}/{$ARGS_TYPE}",
        arguments = listOf(navArgument(ARGS_TYPE) { type = NavType.StringType })
    ) {
        it.arguments?.getString(ARGS_TYPE)?.let { argType ->
            WorkoutScreen(
                stageType = WorkoutType.valueOf(argType),
                navigateBack = { navController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }
    }
}

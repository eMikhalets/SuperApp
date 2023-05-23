package com.emikhalets.fitness.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.fitness.domain.entity.WorkoutType
import com.emikhalets.fitness.presentation.menu.MenuScreen
import com.emikhalets.fitness.presentation.stages.StagesScreen
import com.emikhalets.fitness.presentation.workout.WorkoutScreen

private const val ARGS_TYPE = "args_workout_type"

@Composable
fun FitnessNavHost(navController: NavHostController) {
    NavHost(navController, FitnessScreen.ManiMenu.route) {

        composable(FitnessScreen.ManiMenu.route) {
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
                navigateBack = { navController.popBackStack() }
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
                    }
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
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

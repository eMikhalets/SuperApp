package com.emikhalets.fitness

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.feature.workout.navigation.FeatureWorkoutDestination
import com.emikhalets.feature.workout.navigation.featureWorkoutComposable

val applicationFitnessBottomBar: List<AppBottomBarItem> = listOf()

fun NavGraphBuilder.applicationFitnessGraph(navController: NavHostController) {
    navigation(
        startDestination = FeatureWorkoutDestination.Programs,
        route = AppFitnessDestination.NavGraph
    ) {
        featureWorkoutComposable(navController)
    }
}
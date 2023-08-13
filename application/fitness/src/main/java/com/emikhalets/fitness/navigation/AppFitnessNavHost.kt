package com.emikhalets.fitness.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel
import com.emikhalets.fitness.presentation.screens.AppFitnessScreen

val appFitnessBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appFitnessNavGraph(navController: NavHostController) {
    navigation(AppFitnessRoute.Root, AppFitnessRoute.NavGraph) {
        composable(AppFitnessRoute.Root) {
            AppFitnessScreen(
                onBackClick = navController::popBackStack
            )
        }
    }
}
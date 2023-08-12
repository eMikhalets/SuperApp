package com.emikhalets.fitness.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel

val appFitnessBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appFitnessNavGraph(navController: NavHostController) {
    navigation(AppFitnessRoute.Programs, AppFitnessRoute.NavGraph) {
    }
}
package com.emikhalets.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.notes.presentation.navigation.applicationNotes
import com.emikhalets.notes.presentation.navigation.navigateToAppNotes
//import com.emikhalets.fitness.navigation.applicationFitness
//import com.emikhalets.fitness.navigation.navigateToAppFitness
import com.emikhalets.superapp.screen.MainScreen
import com.emikhalets.superapp.utils.AppType

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Main.route,
    ) {
        composable(AppScreen.Main.route) {
            MainScreen(
                navigateToApp = { type -> navController.navigateApp(type) },
                navigateToWidget = { id -> },
                navigateToNewWidget = {},
            )
        }
//        applicationFitness(navController)
        applicationNotes(navController)
    }
}


private fun NavHostController.navigateApp(type: AppType) {
    when (type) {
        AppType.Events -> Unit
        AppType.Finances -> Unit
        AppType.Fitness -> Unit // navigateToAppFitness()
        AppType.MediaLib -> Unit
        AppType.Notes -> navigateToAppNotes()
    }
}
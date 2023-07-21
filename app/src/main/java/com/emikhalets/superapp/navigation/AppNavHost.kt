package com.emikhalets.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.convert.app.AppConvertDestination
import com.emikhalets.convert.app.applicationConvert
import com.emikhalets.core.ui.ApplicationEntity
import com.emikhalets.fitness.navigation.AppFitnessDestination
import com.emikhalets.fitness.navigation.applicationFitness
import com.emikhalets.notes.AppNotesDestination
import com.emikhalets.notes.applicationNotesGraph
import com.emikhalets.superapp.screen.MainScreen

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(navController, AppMainDestination.Main) {
        composable(AppMainDestination.Main) {
            MainScreen(
                navigateToApp = { type -> navController.navigateApp(type) },
                navigateToWidget = { id -> },
                navigateToNewWidget = {},
            )
        }
        applicationConvert(navController) {}
        applicationFitness(navController) {}
        applicationNotesGraph(navController)
    }
}

private fun NavHostController.navigateApp(type: ApplicationEntity) {
    when (type) {
        ApplicationEntity.Convert -> navigate(AppConvertDestination.NavGraph)
        ApplicationEntity.Events -> Unit
        ApplicationEntity.Finances -> Unit
        ApplicationEntity.Fitness -> navigate(AppFitnessDestination.NavGraph)
        ApplicationEntity.MediaLib -> Unit
        ApplicationEntity.Notes -> navigate(AppNotesDestination.NavGraph)
    }
}
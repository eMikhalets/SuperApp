package com.emikhalets.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.convert.AppConvertDestination
import com.emikhalets.convert.applicationConvertGraph
import com.emikhalets.core.ui.ApplicationEntity
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
        applicationConvertGraph(navController)
//        applicationFitnessGraph(navController)
        applicationNotesGraph(navController)
    }
}

private fun NavHostController.navigateApp(type: ApplicationEntity) {
    when (type) {
        ApplicationEntity.Convert -> navigate(AppConvertDestination.NavGraph)
        ApplicationEntity.Events -> Unit
        ApplicationEntity.Finance -> Unit
        ApplicationEntity.Fitness -> Unit //navigate(AppFitnessDestination.NavGraph)
        ApplicationEntity.MediaLib -> Unit
        ApplicationEntity.Notes -> navigate(AppNotesDestination.NavGraph)
    }
}
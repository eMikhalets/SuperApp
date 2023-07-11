package com.emikhalets.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.convert.app.AppConvertDestination
import com.emikhalets.core.common.ApplicationEntity
import com.emikhalets.core.common.logi
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.fitness.navigation.AppFitnessDestination
import com.emikhalets.fitness.navigation.applicationFitness
import com.emikhalets.notes.app.AppNotesDestination
import com.emikhalets.notes.app.applicationNotes
import com.emikhalets.superapp.MainScreen

private const val TAG = "AppMainGraph"

@Composable
fun AppNavHost(
    navController: NavHostController,
    bottomBarList: (List<AppBottomBarItem>) -> Unit,
) {
    NavHost(navController, AppMainDestination.Main) {
        composable(AppMainDestination.Main) {
            logi(TAG, "Composable MainScreen")
            bottomBarList(emptyList())
            MainScreen(
                navigateToApp = { type ->
                    logi(TAG, "Navigate to application: $type")
                    navController.navigateApp(type)
                },
                navigateToWidget = { id ->
                    logi(TAG, "Navigate to widget: id = $id")
                },
                navigateToNewWidget = {},
            )
        }
        applicationFitness(navController, bottomBarList)
        applicationNotes(navController, bottomBarList)
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
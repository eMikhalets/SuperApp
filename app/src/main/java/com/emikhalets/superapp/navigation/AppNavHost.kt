package com.emikhalets.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.core.common.ApplicationItem
import com.emikhalets.core.common.logi
import com.emikhalets.core.navigation.AppBottomBarItem
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
        applicationNotes(navController, bottomBarList)
    }
}

private fun NavHostController.navigateApp(type: ApplicationItem) {
    when (type) {
        ApplicationItem.Events -> Unit
        ApplicationItem.Finances -> Unit
        ApplicationItem.Fitness -> Unit
        ApplicationItem.MediaLib -> Unit
        ApplicationItem.Notes -> navigate(AppNotesDestination.NavGraph)
    }
}
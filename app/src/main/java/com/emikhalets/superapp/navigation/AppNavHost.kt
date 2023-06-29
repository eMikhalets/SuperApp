package com.emikhalets.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.notes.app.AppNotesDestination
import com.emikhalets.notes.app.applicationNotes
import com.emikhalets.superapp.MainScreen
import com.emikhalets.superapp.utils.AppType

@Composable
fun AppNavHost(
    navController: NavHostController,
    bottomBarList: (List<AppBottomBarItem>) -> Unit,
) {
    NavHost(navController, AppMainDestination.Main) {
        composable(AppMainDestination.Main) {
            bottomBarList(emptyList())
            MainScreen(
                navigateToApp = { type -> navController.navigateApp(type) },
                navigateToWidget = { id -> },
                navigateToNewWidget = {},
            )
        }
        applicationNotes(navController, bottomBarList)
    }
}

private fun NavHostController.navigateApp(type: AppType) {
    when (type) {
        AppType.Events -> Unit
        AppType.Finances -> Unit
        AppType.Fitness -> Unit
        AppType.MediaLib -> Unit
        AppType.Notes -> navigate(AppNotesDestination.NavGraph)
    }
}
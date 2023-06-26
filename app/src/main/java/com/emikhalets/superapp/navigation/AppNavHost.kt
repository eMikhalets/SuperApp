package com.emikhalets.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.core.AppBottomBarItem
import com.emikhalets.notes.presentation.navigation.applicationNotes
import com.emikhalets.notes.presentation.navigation.navigateToAppNotes
import com.emikhalets.superapp.MainScreen
import com.emikhalets.superapp.utils.AppType

@Composable
fun AppNavHost(
    navController: NavHostController,
    bottomBarList: (List<AppBottomBarItem>) -> Unit,
) {
    NavHost(navController, AppScreen.Main.route) {
        composable(AppScreen.Main.route) {
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
        AppType.Notes -> navigateToAppNotes()
    }
}
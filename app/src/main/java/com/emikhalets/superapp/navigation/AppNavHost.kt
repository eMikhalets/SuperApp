package com.emikhalets.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.core.AppScreen
import com.emikhalets.notes.presentation.navigation.applicationNotes
import com.emikhalets.superapp.MainScreen
import com.emikhalets.superapp.utils.AppType

@Composable
fun AppNavHost(
    navController: NavHostController,
    bottomBarList: (List<AppScreen>) -> Unit,
) {
    NavHost(navController, AppMainScreen.Main.route) {
        composable(AppMainScreen.Main.route) {
            bottomBarList(emptyList())
            MainScreen(
                navigateToApp = { type -> navController.navigateApp(type) },
                navigateToWidget = { id -> },
                navigateToNewWidget = {},
            )
        }
        applicationNotes(AppMainScreen.APP_NOTES_ROUTE, navController, bottomBarList)
    }
}

private fun NavHostController.navigateApp(type: AppType) {
    when (type) {
        AppType.Events -> Unit
        AppType.Finances -> Unit
        AppType.Fitness -> Unit
        AppType.MediaLib -> Unit
        AppType.Notes -> navigate(AppMainScreen.APP_NOTES_ROUTE)
    }
}
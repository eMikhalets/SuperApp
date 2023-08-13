package com.emikhalets.events.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel
import com.emikhalets.events.presentation.screens.AppEventsScreen

val appEventsBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appEventsNavGraph(navController: NavHostController) {
    navigation(AppEventsRoute.Root, AppEventsRoute.NavGraph) {
        composable(AppEventsRoute.Root) {
            AppEventsScreen(
                onBackClick = navController::popBackStack
            )
        }
    }
}
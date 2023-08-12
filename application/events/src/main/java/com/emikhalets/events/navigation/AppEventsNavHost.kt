package com.emikhalets.events.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel

val appEventsBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appEventsNavGraph(navController: NavHostController) {
    navigation(AppEventsRoute.Events, AppEventsRoute.NavGraph) {
    }
}
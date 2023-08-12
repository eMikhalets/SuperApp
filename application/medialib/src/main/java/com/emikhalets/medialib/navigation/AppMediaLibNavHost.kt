package com.emikhalets.medialib.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel

val appMediaLibBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appMediaLibNavGraph(navController: NavHostController) {
    navigation(AppMediaLibRoute.MediaLib, AppMediaLibRoute.NavGraph) {
    }
}
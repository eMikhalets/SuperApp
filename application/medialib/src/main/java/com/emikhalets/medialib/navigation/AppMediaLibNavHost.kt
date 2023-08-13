package com.emikhalets.medialib.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel
import com.emikhalets.medialib.presentation.screens.AppMediaLibScreen

val appMediaLibBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appMediaLibNavGraph(navController: NavHostController) {
    navigation(AppMediaLibRoute.Root, AppMediaLibRoute.NavGraph) {
        composable(AppMediaLibRoute.Root) {
            AppMediaLibScreen(
                onBackClick = navController::popBackStack
            )
        }
    }
}
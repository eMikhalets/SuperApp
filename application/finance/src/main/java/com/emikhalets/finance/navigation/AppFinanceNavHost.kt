package com.emikhalets.finance.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel
import com.emikhalets.finance.presentation.screens.AppFinanceScreen

val appFinanceBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appFinanceNavGraph(navController: NavHostController) {
    navigation(AppFinanceRoute.Root, AppFinanceRoute.NavGraph) {
        composable(AppFinanceRoute.Root) {
            AppFinanceScreen(
                onBackClick = navController::popBackStack
            )
        }
    }
}
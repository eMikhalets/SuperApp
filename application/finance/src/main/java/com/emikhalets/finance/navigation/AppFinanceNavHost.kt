package com.emikhalets.finance.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel

val appFinanceBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appFinanceNavGraph(navController: NavHostController) {
    navigation(AppFinanceRoute.Transactions, AppFinanceRoute.NavGraph) {
    }
}
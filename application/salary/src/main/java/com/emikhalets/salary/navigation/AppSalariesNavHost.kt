package com.emikhalets.salary.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel
import com.emikhalets.salary.presentation.SalariesGraphScreen

val appSalaryBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appSalaryNavGraph(navController: NavHostController) {
    navigation(AppSalariesRoute.SalaryGraph, AppSalariesRoute.NavGraph) {
        composable(AppSalariesRoute.SalaryGraph) {
            SalariesGraphScreen(
                navigateBack = navController::popBackStack,
                viewModel = hiltViewModel(),
            )
        }
    }
}
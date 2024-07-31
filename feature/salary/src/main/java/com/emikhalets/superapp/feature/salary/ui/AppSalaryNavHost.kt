package com.emikhalets.superapp.feature.salary.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.feature.salary.ui.AppSalaryRoute.Chart
import com.emikhalets.superapp.feature.salary.ui.AppSalaryRoute.NavGraph
import com.emikhalets.superapp.feature.salary.ui.chart.ChartScreen

fun NavGraphBuilder.appSalaryNavGraph(
    navController: NavHostController,
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    navigation(Chart, NavGraph) {
        composable(Chart) {
            onSetScreenOrientation(AppOrientationType.Portrait)
            ChartScreen(
                navigateBack = navController::popBackStack,
                viewModel = hiltViewModel(),
            )
        }
    }
}
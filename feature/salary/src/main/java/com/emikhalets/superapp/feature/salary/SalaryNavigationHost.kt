package com.emikhalets.superapp.feature.salary

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.feature.salary.ui.chart.ChartScreen
import kotlinx.serialization.Serializable

@Serializable
data object SalaryChartRoute

fun NavGraphBuilder.salaryNavigation(
    navController: NavHostController,
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    onSetScreenOrientation(AppOrientationType.Portrait)
    composable<SalaryChartRoute> {
        ChartScreen(
            navigateBack = navController::popBackStack,
            viewModel = hiltViewModel(),
        )
    }
}
package com.emikhalets.superapp.feature.salary

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.feature.salary.AppSalariesRoute.Chart
import com.emikhalets.superapp.feature.salary.AppSalariesRoute.KEY_ITEM_ID
import com.emikhalets.superapp.feature.salary.AppSalariesRoute.NavGraph
import com.emikhalets.superapp.feature.salary.AppSalariesRoute.itemId
import com.emikhalets.superapp.feature.salary.chart.ChartScreen

fun NavGraphBuilder.appSalaryNavGraph(
    navController: NavHostController,
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    navigation(Chart, NavGraph) {
        composable(Chart) {
            onSetScreenOrientation(AppOrientationType.Landscape)
            ChartScreen(
                navigateBack = navController::popBackStack,
                navigateEditItem = { id -> navController.navigate(itemId(id)) },
                viewModel = hiltViewModel(),
            )
        }
        composable(
            route = itemId(),
            arguments = listOf(navArgument(KEY_ITEM_ID) { type = NavType.LongType })
        ) {
            val id = it.arguments?.getLong(KEY_ITEM_ID) ?: 0
            onSetScreenOrientation(AppOrientationType.Landscape)
            ChartScreen(
                navigateBack = navController::popBackStack,
                viewModel = hiltViewModel(),
            )
        }
    }
}
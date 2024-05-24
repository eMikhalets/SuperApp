package com.emikhalets.superapp.feature.salary

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.feature.salary.AppSalariesRoute.Chart
import com.emikhalets.superapp.feature.salary.AppSalariesRoute.EditItem
import com.emikhalets.superapp.feature.salary.AppSalariesRoute.KEY_ITEM_ID
import com.emikhalets.superapp.feature.salary.AppSalariesRoute.NavGraph
import com.emikhalets.superapp.feature.salary.AppSalariesRoute.editItemArgs
import com.emikhalets.superapp.feature.salary.AppSalariesRoute.editItemRoute
import com.emikhalets.superapp.feature.salary.chart.ChartScreen
import com.emikhalets.superapp.feature.salary.edit_item.EditItemScreen

fun NavGraphBuilder.appSalaryNavGraph(
    navController: NavHostController,
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    navigation(Chart, NavGraph) {
        composable(Chart) {
            onSetScreenOrientation(AppOrientationType.Landscape)
            ChartScreen(
                navigateBack = navController::popBackStack,
                navigateEditItem = { id -> navController.navigate(editItemRoute(id)) },
                viewModel = hiltViewModel(),
            )
        }
        composable(route = EditItem, arguments = editItemArgs()) {
            onSetScreenOrientation(AppOrientationType.Portrait)
            EditItemScreen(
                itemId = it.arguments?.getLong(KEY_ITEM_ID) ?: 0,
                navigateBack = navController::popBackStack,
                viewModel = hiltViewModel(),
            )
        }
    }
}
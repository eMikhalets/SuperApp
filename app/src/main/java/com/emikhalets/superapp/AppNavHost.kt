package com.emikhalets.superapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.core.ui.AppFeature
import com.emikhalets.superapp.feature.convert.ConvertCurrenciesRoute
import com.emikhalets.superapp.feature.convert.convertNavigation
import com.emikhalets.superapp.feature.notes.TasksListRoute
import com.emikhalets.superapp.feature.notes.notesNavigation
import com.emikhalets.superapp.feature.salary.SalaryChartRoute
import com.emikhalets.superapp.feature.salary.salaryNavigation
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    NavHost(navController, MainRoute) {
        composable<MainRoute> {
            onSetScreenOrientation(AppOrientationType.Portrait)
            FeaturesScreen(
                navigateToFeature = { navigateToFeature(navController, it) },
            )
        }
        if (AppFeature.Convert.visible) {
            convertNavigation(navController, onSetScreenOrientation)
        }
        if (AppFeature.Notes.visible) {
            notesNavigation(navController, onSetScreenOrientation)
        }
        if (AppFeature.Salary.visible) {
            salaryNavigation(navController, onSetScreenOrientation)
        }
    }
}

private fun navigateToFeature(navController: NavHostController, feature: AppFeature) {
    when (feature) {
        AppFeature.Convert -> navController.navigate(ConvertCurrenciesRoute)
        AppFeature.Notes -> navController.navigate(TasksListRoute)
        AppFeature.Salary -> navController.navigate(SalaryChartRoute)
    }
}
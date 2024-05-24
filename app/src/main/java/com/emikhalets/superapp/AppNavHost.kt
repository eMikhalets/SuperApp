package com.emikhalets.superapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.superapp.core.ui.AppFeature
import com.emikhalets.superapp.AppRoute.Main
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.feature.salary.AppSalaryRoute
import com.emikhalets.superapp.feature.salary.appSalaryNavGraph

@Composable
fun AppNavHost(
    navController: NavHostController,
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    NavHost(navController, Main) {
        composable(Main) {
            onSetScreenOrientation(AppOrientationType.Portrait)
            FeaturesScreen(
                navigateToFeature = { navigateToFeature(navController, it) },
            )
        }
        if (AppFeature.Salary.visible) {
            appSalaryNavGraph(navController, onSetScreenOrientation)
        }
    }
}

private fun navigateToFeature(navController: NavHostController, feature: AppFeature) {
    when (feature) {
        AppFeature.Salary -> navController.navigate(AppSalaryRoute.NavGraph)
    }
}
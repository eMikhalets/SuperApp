package com.emikhalets.superapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.superapp.AppRoute.Main
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.core.ui.AppFeature
import com.emikhalets.superapp.feature.convert.ui.AppConvertRoute
import com.emikhalets.superapp.feature.convert.ui.appConvertNavGraph
import com.emikhalets.superapp.feature.salary.ui.AppSalaryRoute
import com.emikhalets.superapp.feature.salary.ui.appSalaryNavGraph

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
        if (AppFeature.Convert.visible) {
            appConvertNavGraph(navController, onSetScreenOrientation)
        }
        if (AppFeature.Salary.visible) {
            appSalaryNavGraph(navController, onSetScreenOrientation)
        }
    }
}

private fun navigateToFeature(navController: NavHostController, feature: AppFeature) {
    when (feature) {
        AppFeature.Convert -> navController.navigate(AppConvertRoute.NavGraph)
        AppFeature.Salary -> navController.navigate(AppSalaryRoute.NavGraph)
    }
}
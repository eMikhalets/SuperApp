package com.emikhalets.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.convert.navigation.AppConvertRoute
import com.emikhalets.convert.navigation.appConvertNavGraph
import com.emikhalets.core.common.ApplicationItem
import com.emikhalets.core.ui.BottomBarModel
import com.emikhalets.salary.navigation.AppSalariesRoute
import com.emikhalets.salary.navigation.appSalaryNavGraph
import com.emikhalets.superapp.MainScreen

val appBottomBar: List<BottomBarModel> = listOf()

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, AppRoute.Main) {
        composable(AppRoute.Main) {
            MainScreen(
                navigateToApplication = { navigateApplication(navController, it) },
            )
        }
        appConvertNavGraph(navController)
        appSalaryNavGraph(navController)
    }
}

private fun navigateApplication(navController: NavHostController, application: ApplicationItem) {
    when (application) {
        ApplicationItem.Convert -> navController.navigate(AppConvertRoute.NavGraph)
        ApplicationItem.Salaries -> navController.navigate(AppSalariesRoute.NavGraph)
    }
}
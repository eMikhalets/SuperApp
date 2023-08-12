package com.emikhalets.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.convert.navigation.AppConvertRoute
import com.emikhalets.convert.navigation.appConvertNavGraph
import com.emikhalets.core.ui.BottomBarModel
import com.emikhalets.events.navigation.AppEventsRoute
import com.emikhalets.events.navigation.appEventsNavGraph
import com.emikhalets.finance.navigation.AppFinanceRoute
import com.emikhalets.finance.navigation.appFinanceNavGraph
import com.emikhalets.fitness.navigation.AppFitnessRoute
import com.emikhalets.fitness.navigation.appFitnessNavGraph
import com.emikhalets.medialib.navigation.AppMediaLibRoute
import com.emikhalets.medialib.navigation.appMediaLibNavGraph
import com.emikhalets.notes.navigation.AppNotesRoute
import com.emikhalets.notes.navigation.appNotesNavGraph
import com.emikhalets.superapp.ApplicationType
import com.emikhalets.superapp.screen.MainScreen

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
        appEventsNavGraph(navController)
        appFinanceNavGraph(navController)
        appFitnessNavGraph(navController)
        appMediaLibNavGraph(navController)
        appNotesNavGraph(navController)
    }
}

private fun navigateApplication(navController: NavHostController, application: ApplicationType) {
    when (application) {
        ApplicationType.Convert -> navController.navigate(AppConvertRoute.NavGraph)
        ApplicationType.Events -> navController.navigate(AppEventsRoute.NavGraph)
        ApplicationType.Finance -> navController.navigate(AppFinanceRoute.NavGraph)
        ApplicationType.Fitness -> navController.navigate(AppFitnessRoute.NavGraph)
        ApplicationType.MediaLib -> navController.navigate(AppMediaLibRoute.NavGraph)
        ApplicationType.Notes -> navController.navigate(AppNotesRoute.NavGraph)
    }
}
package com.emikhalets.convert

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.emikhalets.core.navigation.AppBottomBarItem

private const val TAG = "AppConvertGraph"

val applicationConvertBottomBar: List<AppBottomBarItem> = listOf()

fun NavGraphBuilder.applicationConvertGraph(navController: NavHostController) {
//    navigation(
////        startDestination = FeatureTasksDestination.Tasks,
////        route = AppNotesDestination.NavGraph
//    ) {
//        featureNotesComposable(navController)
//        featureTasksComposable(navController)
//    }
//    navigation(AppConvertDestination.Currencies, AppConvertDestination.NavGraph) {
//        composable(AppConvertDestination.Currencies) {
//            logi(TAG, "Composable CurrenciesScreen")
//            CurrenciesScreen(
//                navigateBack = {
//                    logi(TAG, "Navigate back")
//                    navController.popBackStack()
//                },
//                viewModel = hiltViewModel()
//            )
//        }
//    }
}

package com.emikhalets.feature.currencies_convert.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.featureCurrenciesConvertComposable(navController: NavHostController) {
    composable(FeatureCurrenciesConvertDestination.Currencies) {
//        NotesListScreen(
//            navigateBack = navController::popBackStack,
//            viewModel = hiltViewModel()
//        )
    }
//    composable(AppConvertDestination.Currencies) {
//        logi(TAG, "Composable CurrenciesScreen")
//        CurrenciesScreen(
//            navigateBack = {
//                logi(TAG, "Navigate back")
//                navController.popBackStack()
//            },
//            viewModel = hiltViewModel()
//        )
//    }
}

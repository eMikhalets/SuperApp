package com.emikhalets.feature.currencies_convert.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.emikhalets.feature.transactions.presentation.CurrenciesScreen

fun NavGraphBuilder.featureCurrenciesConvertComposable(navController: NavHostController) {
    composable(FeatureCurrenciesConvertDestination.Currencies) {
        CurrenciesScreen(
            navigateBack = navController::popBackStack,
            viewModel = hiltViewModel()
        )
    }
}

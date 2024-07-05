package com.emikhalets.superapp.feature.convert.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesScreen

fun NavGraphBuilder.appConvertNavGraph(
    navController: NavHostController,
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    navigation(AppConvertRoute.Currencies, AppConvertRoute.NavGraph) {
        composable(AppConvertRoute.Currencies) {
            onSetScreenOrientation(AppOrientationType.Portrait)
            CurrenciesScreen(
                navigateBack = navController::popBackStack,
                viewModel = hiltViewModel(),
            )
        }
    }
}
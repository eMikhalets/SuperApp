package com.emikhalets.superapp.feature.convert

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesScreen
import kotlinx.serialization.Serializable

@Serializable
data object ConvertCurrenciesRoute

fun NavGraphBuilder.convertNavigation(
    navController: NavHostController,
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    composable<ConvertCurrenciesRoute> {
        onSetScreenOrientation(AppOrientationType.Portrait)
        CurrenciesScreen(
            navigateBack = navController::popBackStack,
            viewModel = hiltViewModel(),
        )
    }
}
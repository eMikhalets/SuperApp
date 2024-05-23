package com.emikhalets.convert.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.convert.presentation.screens.CurrenciesScreen
import com.emikhalets.core.ui.BottomBarModel

val appConvertBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appConvertNavGraph(
    navController: NavHostController,
    onSetScreenPortrait: (Boolean) -> Unit,
) {
    navigation(AppConvertRoute.Currencies, AppConvertRoute.NavGraph) {
        composable(AppConvertRoute.Currencies) {
            onSetScreenPortrait(true)
            CurrenciesScreen(
                navigateBack = navController::popBackStack,
                viewModel = hiltViewModel(),
            )
        }
    }
}
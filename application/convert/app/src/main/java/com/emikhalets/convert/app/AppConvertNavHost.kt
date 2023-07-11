package com.emikhalets.convert.app

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.emikhalets.convert.presentation.screens.currencies.CurrenciesScreen
import com.emikhalets.core.common.logi
import com.emikhalets.core.navigation.AppBottomBarItem

private const val TAG = "AppConvertGraph"

private val bottomBarItems: List<AppBottomBarItem> = listOf()

fun NavGraphBuilder.applicationConvert(
    navController: NavHostController,
    bottomBarList: (List<AppBottomBarItem>) -> Unit,
) {
    navigation(AppConvertDestination.Currencies, AppConvertDestination.NavGraph) {
        composable(AppConvertDestination.Currencies) {
            logi(TAG, "Composable CurrenciesScreen")
            CurrenciesScreen(
                navigateBack = {
                    logi(TAG, "Navigate back")
                    navController.popBackStack()
                },
                viewModel = hiltViewModel()
            )
        }
    }
}

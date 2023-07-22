package com.emikhalets.convert

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.feature.currencies_convert.navigation.FeatureCurrenciesConvertDestination
import com.emikhalets.feature.currencies_convert.navigation.featureCurrenciesConvertComposable

val applicationConvertBottomBar: List<AppBottomBarItem> = listOf()

fun NavGraphBuilder.applicationConvertGraph(navController: NavHostController) {
    navigation(
        startDestination = FeatureCurrenciesConvertDestination.Currencies,
        route = AppConvertDestination.NavGraph
    ) {
        featureCurrenciesConvertComposable(navController)
    }
}

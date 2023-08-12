package com.emikhalets.convert.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel

val appConvertBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appConvertNavGraph(navController: NavHostController) {
    navigation(AppConvertRoute.Currencies, AppConvertRoute.NavGraph) {
    }
}
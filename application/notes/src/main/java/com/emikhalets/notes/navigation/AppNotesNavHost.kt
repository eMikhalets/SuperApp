package com.emikhalets.notes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel
import com.emikhalets.notes.presentation.screens.AppNotesScreen

val appNotesBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appNotesNavGraph(navController: NavHostController) {
    navigation(AppNotesRoute.Root, AppNotesRoute.NavGraph) {
        composable(AppNotesRoute.Root) {
            AppNotesScreen(
                onBackClick = navController::popBackStack
            )
        }
    }
}
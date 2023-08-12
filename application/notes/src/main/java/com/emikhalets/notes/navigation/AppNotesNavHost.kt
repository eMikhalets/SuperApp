package com.emikhalets.notes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.emikhalets.core.ui.BottomBarModel

val appNotesBottomBar: List<BottomBarModel> = listOf()

fun NavGraphBuilder.appNotesNavGraph(navController: NavHostController) {
    navigation(AppNotesRoute.Tasks, AppNotesRoute.NavGraph) {
    }
}
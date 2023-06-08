package com.emikhalets.notes.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.emikhalets.fitness.presentation.stages.StagesScreen
import com.emikhalets.notes.MenuScreen

fun NavController.navigateToAppNotes(navOptions: NavOptions? = null) {
    this.navigate(NotesScreen.Main.route, navOptions)
}

fun NavGraphBuilder.applicationNotes(navController: NavHostController) {

    composable(NotesScreen.Main.route) {
        MenuScreen(
            navigateBack = { navController.popBackStack() },
            viewModel = hiltViewModel()
        )
    }

    composable(NotesScreen.Pager.route) {
        StagesScreen(
            navigateBack = { navController.popBackStack(NotesScreen.Main.route, true) },
            viewModel = hiltViewModel()
        )
    }

    composable(NotesScreen.Tasks.route) {
        StagesScreen(
            navigateBack = { navController.popBackStack() },
            viewModel = hiltViewModel()
        )
    }

    composable(NotesScreen.Notes.route) {
        StagesScreen(
            navigateBack = { navController.popBackStack() },
            viewModel = hiltViewModel()
        )
    }

    composable(NotesScreen.Settings.route) {
        StagesScreen(
            navigateBack = { navController.popBackStack() },
            viewModel = hiltViewModel()
        )
    }
}

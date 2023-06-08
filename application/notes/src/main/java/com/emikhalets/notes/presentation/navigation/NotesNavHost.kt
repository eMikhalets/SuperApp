package com.emikhalets.notes.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.emikhalets.notes.presentation.screens.notes.NotesListScreen

fun NavController.navigateToAppNotes(navOptions: NavOptions? = null) {
    this.navigate(NotesScreen.Notes.route, navOptions)
}

fun NavGraphBuilder.applicationNotes(navController: NavHostController) {

    composable(NotesScreen.Notes.route) {
        NotesListScreen(
            navigateBack = { navController.popBackStack(NotesScreen.Notes.route, true) },
            viewModel = hiltViewModel()
        )
    }
}

package com.emikhalets.feature.notes.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.emikhalets.core.common.logi
import com.emikhalets.feature.notes.presentation.NotesListScreen

private const val TAG = "GraphFeatureNotes"

fun NavGraphBuilder.featureNotesComposable(navController: NavHostController) {
    composable(FeatureNotesDestination.Notes) {
        logi(TAG, "Composable NotesListScreen")
        NotesListScreen(
            navigateBack = {
                logi(TAG, "Navigate back")
                navController.popBackStack()
            },
            viewModel = hiltViewModel()
        )
    }
}

package com.emikhalets.superapp.feature.notes

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.feature.notes.ui.task_list.TasksListScreen
import kotlinx.serialization.Serializable

@Serializable
data object TasksListRoute

fun NavGraphBuilder.notesNavigation(
    navController: NavHostController,
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    onSetScreenOrientation(AppOrientationType.Portrait)
    composable<TasksListRoute> {
        TasksListScreen(
            navigateBack = navController::popBackStack,
            viewModel = hiltViewModel(),
        )
    }
}
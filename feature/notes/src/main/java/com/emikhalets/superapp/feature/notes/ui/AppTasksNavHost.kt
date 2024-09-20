package com.emikhalets.superapp.feature.notes.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.feature.notes.ui.task_list.TasksListScreen
import com.emikhalets.superapp.feature.notes.ui.AppNotesRoute.NavGraph
import com.emikhalets.superapp.feature.notes.ui.AppNotesRoute.TasksList

fun NavGraphBuilder.appNotesNavGraph(
    navController: NavHostController,
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    navigation(TasksList, NavGraph) {
        composable(TasksList) {
            onSetScreenOrientation(AppOrientationType.Landscape)
            TasksListScreen(
                navigateBack = navController::popBackStack,
                viewModel = hiltViewModel(),
            )
        }
    }
}
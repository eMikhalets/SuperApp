package com.emikhalets.superapp.feature.tasks

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.emikhalets.superapp.feature.tasks.AppTasksRoute.NavGraph
import com.emikhalets.superapp.feature.tasks.AppTasksRoute.TasksList

fun NavGraphBuilder.appTasksNavGraph(
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
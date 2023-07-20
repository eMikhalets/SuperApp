package com.emikhalets.feature.tasks.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.emikhalets.core.common.logi
import com.emikhalets.feature.tasks.presentation.TasksListScreen

private const val TAG = "GraphFeatureTasks"

fun NavGraphBuilder.featureTasksGraph(navController: NavHostController) {
    composable(FeatureTasksDestination.Tasks) {
        logi(TAG, "Composable TasksListScreen")
        TasksListScreen(
            navigateBack = {
                logi(TAG, "Navigate back")
                navController.popBackStack()
            },
            viewModel = hiltViewModel()
        )
    }
}

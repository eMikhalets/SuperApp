package com.emikhalets.notes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.feature.tasks.navigation.FeatureTasksDestination
import com.emikhalets.feature.tasks.navigation.featureTasksGraph

private val bottomBarItems: List<AppBottomBarItem> = listOf(
//    AppBottomBarItem.getInstance(
//        FeatureTasksDestination.Tasks,
//        Icons.Rounded.Checklist
//    ),
//    AppBottomBarItem.getInstance(
//        FeatureNotesDestination.Notes,
//        Icons.Rounded.EditNote
//    )
)

fun NavGraphBuilder.applicationNotesGraph(
    navController: NavHostController,
    setBottomBar: (List<AppBottomBarItem>) -> Unit,
) {
    setBottomBar(bottomBarItems)
    navigation(FeatureTasksDestination.Tasks, AppNotesDestination.NavGraph) {
        featureTasksGraph(navController)
    }
}

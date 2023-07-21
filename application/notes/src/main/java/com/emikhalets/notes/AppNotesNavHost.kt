package com.emikhalets.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.EditNote
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.feature.notes.navigation.FeatureNotesDestination
import com.emikhalets.feature.notes.navigation.featureNotesComposable
import com.emikhalets.feature.tasks.navigation.FeatureTasksDestination
import com.emikhalets.feature.tasks.navigation.featureTasksComposable

val applicationNotesBottomBar: List<AppBottomBarItem> = listOf(
    AppBottomBarItem.getInstance(
        FeatureTasksDestination.Tasks,
        Icons.Rounded.Checklist
    ),
    AppBottomBarItem.getInstance(
        FeatureNotesDestination.Notes,
        Icons.Rounded.EditNote
    )
)

fun NavGraphBuilder.applicationNotesGraph(navController: NavHostController) {
    navigation(
        startDestination = FeatureTasksDestination.Tasks,
        route = AppNotesDestination.NavGraph
    ) {
        featureNotesComposable(navController)
        featureTasksComposable(navController)
    }
}

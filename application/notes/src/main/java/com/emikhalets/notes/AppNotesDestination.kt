package com.emikhalets.notes

import com.emikhalets.core.navigation.AppDestination
import com.emikhalets.feature.tasks.navigation.FeatureTasksDestination

object AppNotesDestination : AppDestination {

    const val NavGraph: String = "graph_application_notes"
    const val BottomBarTrigger: String = FeatureTasksDestination.Tasks
}
package com.emikhalets.fitness

import com.emikhalets.core.navigation.AppDestination
import com.emikhalets.feature.workout.navigation.FeatureWorkoutDestination

object AppFitnessDestination : AppDestination {

    const val NavGraph: String = "graph_application_fitness"
    const val BottomBarTrigger: String = FeatureWorkoutDestination.Programs
}
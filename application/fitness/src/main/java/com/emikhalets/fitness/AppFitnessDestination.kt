package com.emikhalets.fitness

import com.emikhalets.core.navigation.AppDestination
import com.emikhalets.feature.workout.navigation.FeatureWorkoutDestination

object AppFitnessDestination : AppDestination {

    const val NavGraph: String = "graph_application_fitness"
    const val BottomBarTrigger: String = FeatureWorkoutDestination.Programs

//    const val ProgramWithArgs: String = "$Program/{${AppFitnessArgs.ProgramId}}"
//    fun programWithArgs(id: Long?): String = "$Program/${id ?: AppCode.IdNull}"
//    val programArgsList = listOf(navArgument(AppFitnessArgs.ProgramId) { type = NavType.LongType })
//
//    const val ProgramEditWithArgs: String = "$ProgramEdit/{${AppFitnessArgs.ProgramId}}"
//    fun programEditWithArgs(id: Long?): String = "$ProgramEdit/${id ?: AppCode.IdNull}"
//    val programEditArgsList =
//        listOf(navArgument(AppFitnessArgs.ProgramId) { type = NavType.LongType })
}
package com.emikhalets.feature.workout.navigation

import com.emikhalets.core.navigation.AppDestination

object FeatureWorkoutDestination : AppDestination {

    const val Programs: String = "feature_workout_programs"
    const val AddProgram: String = "feature_workout_add_program"

//    const val ProgramWithArgs: String = "$Program/{${com.emikhalets.feature.workout.navigation.AppFitnessArgs.ProgramId}}"
//    fun programWithArgs(id: Long?): String = "$Program/${id ?: AppCode.IdNull}"
//    val programArgsList = listOf(navArgument(com.emikhalets.feature.workout.navigation.AppFitnessArgs.ProgramId) { type = NavType.LongType })
//
//    const val ProgramEditWithArgs: String = "$ProgramEdit/{${com.emikhalets.feature.workout.navigation.AppFitnessArgs.ProgramId}}"
//    fun programEditWithArgs(id: Long?): String = "$ProgramEdit/${id ?: AppCode.IdNull}"
//    val programEditArgsList =
//        listOf(navArgument(com.emikhalets.feature.workout.navigation.AppFitnessArgs.ProgramId) { type = NavType.LongType })
}
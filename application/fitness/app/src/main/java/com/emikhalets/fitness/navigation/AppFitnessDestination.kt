package com.emikhalets.fitness.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.emikhalets.core.common.AppCode
import com.emikhalets.core.navigation.AppDestination

object AppFitnessDestination : AppDestination {

    const val NavGraph: String = "app_fitness_graph"

    const val Programs: String = "app_fitness_programs"
    const val Program: String = "app_fitness_program"

    const val ProgramWithArgs: String = "$Program/{${AppFitnessArgs.ProgramId}}"
    fun programWithArgs(id: Long?): String = "$Program/${id ?: AppCode.IdNull}"
    val programArgsList = listOf(navArgument(AppFitnessArgs.ProgramId) { type = NavType.LongType })
    fun getProgramArgsId(entry: NavBackStackEntry) =
        entry.arguments?.getLong(AppFitnessArgs.ProgramId) ?: AppCode.IdNull
}
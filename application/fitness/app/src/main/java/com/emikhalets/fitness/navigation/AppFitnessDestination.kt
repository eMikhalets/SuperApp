package com.emikhalets.fitness.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.emikhalets.core.common.AppCode
import com.emikhalets.core.navigation.AppDestination

object AppFitnessDestination : AppDestination {

    const val NavGraph: String = "app_fitness_graph"

    const val Programs: String = "app_fitness_programs"
    const val Program: String = "app_fitness_program"
    const val ProgramEdit: String = "app_fitness_program_edit"

    const val ProgramWithArgs: String = "$Program/{${AppFitnessArgs.ProgramId}}"
    fun programWithArgs(id: Long?): String = "$Program/${id ?: AppCode.IdNull}"
    val programArgsList = listOf(navArgument(AppFitnessArgs.ProgramId) { type = NavType.LongType })

    const val ProgramEditWithArgs: String = "$ProgramEdit/{${AppFitnessArgs.ProgramId}}"
    fun programEditWithArgs(id: Long?): String = "$ProgramEdit/${id ?: AppCode.IdNull}"
    val programEditArgsList =
        listOf(navArgument(AppFitnessArgs.ProgramId) { type = NavType.LongType })
}
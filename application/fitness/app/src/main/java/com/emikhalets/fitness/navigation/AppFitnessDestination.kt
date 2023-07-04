package com.emikhalets.fitness.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.emikhalets.core.common.AppCode
import com.emikhalets.core.navigation.AppDestination

object AppFitnessDestination : AppDestination {

    const val NavGraph: String = "app_fitness_graph"

    const val Tasks: String = "app_fitness_tasks"
    const val Notes: String = "app_fitness_notes"
    const val Note: String = "app_fitness_note"
    const val Settings: String = "app_fitness_settings"

    const val NoteWithArgs: String = "$Note/{${AppFitnessArgs.NoteId}}"
    fun noteWithArgs(id: Long?): String = "$Note/${id ?: AppCode.IdNull}"
    val noteArgsList = listOf(navArgument(AppFitnessArgs.NoteId) { type = NavType.LongType })
    fun getNoteArgsId(entry: NavBackStackEntry) =
        entry.arguments?.getLong(AppFitnessArgs.NoteId) ?: AppCode.IdNull
}
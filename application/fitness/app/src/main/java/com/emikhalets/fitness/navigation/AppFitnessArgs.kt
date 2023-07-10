package com.emikhalets.fitness.navigation

import androidx.navigation.NavBackStackEntry
import com.emikhalets.core.common.AppCode
import com.emikhalets.core.navigation.AppDestination

object AppFitnessArgs : AppDestination {

    const val ProgramId: String = "args_program_id"
    fun getProgramId(entry: NavBackStackEntry) =
        entry.arguments?.getLong(ProgramId) ?: AppCode.IdNull
}
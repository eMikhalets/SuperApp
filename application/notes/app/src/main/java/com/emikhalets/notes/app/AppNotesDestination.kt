package com.emikhalets.notes.app

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.emikhalets.core.common.AppCode
import com.emikhalets.core.navigation.AppDestination

object AppNotesDestination : AppDestination {

    const val NavGraph: String = "app_notes_graph"

    const val Tasks: String = "app_notes_tasks"
    const val Notes: String = "app_notes_notes"
    const val Note: String = "app_notes_note"
    const val Settings: String = "app_notes_settings"

    const val NoteWithArgs: String = "$Note/{${AppNotesArgs.NoteId}}"
    fun noteWithArgs(id: Long?): String = "$Note/${id ?: AppCode.NoId}"
    val noteArgsList = listOf(navArgument(AppNotesArgs.NoteId) { type = NavType.LongType })
    fun getNoteArgsId(entry: NavBackStackEntry) =
        entry.arguments?.getLong(AppNotesArgs.NoteId) ?: AppCode.NoId
}
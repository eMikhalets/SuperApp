package com.emikhalets.notes

import com.emikhalets.core.navigation.AppDestination

object AppNotesDestination : AppDestination {

    const val NavGraph: String = "graph_application_notes"

//    const val Tasks: String = "app_notes_tasks"
//    const val Notes: String = "app_notes_notes"
//    const val Note: String = "app_notes_note"
//    const val Settings: String = "app_notes_settings"

//    const val NoteWithArgs: String = "$Note/{${AppNotesArgs.NoteId}}"
//    fun noteWithArgs(id: Long?): String = "$Note/${id ?: AppCode.IdNull}"
//    val noteArgsList = listOf(navArgument(AppNotesArgs.NoteId) { type = NavType.LongType })
//    fun getNoteArgsId(entry: NavBackStackEntry) =
//        entry.arguments?.getLong(AppNotesArgs.NoteId) ?: AppCode.IdNull
}
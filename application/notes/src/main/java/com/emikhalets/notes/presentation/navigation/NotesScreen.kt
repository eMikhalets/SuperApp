package com.emikhalets.notes.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.core.AppBottomBarItem

sealed class NotesScreen(val route: String, val icon: ImageVector) {

    object Main : NotesScreen(
        "app_notes_main",
        Icons.Default.Android
    )

    object NotesList : NotesScreen(
        "app_notes_list",
        Icons.Rounded.EditNote
    ), AppBottomBarItem

    object NoteItem : NotesScreen(
        "app_note_item",
        Icons.Default.Android
    )

    object Settings : NotesScreen(
        "app_notes_settings",
        Icons.Rounded.Settings
    ), AppBottomBarItem
}

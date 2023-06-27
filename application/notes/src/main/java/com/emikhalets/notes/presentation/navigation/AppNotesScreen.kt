package com.emikhalets.notes.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.core.AppScreen

sealed class AppNotesScreen(val route: String, val icon: ImageVector) {

    object NotesList : AppNotesScreen(
        "app_notes_list_notes",
        Icons.Rounded.EditNote
    ), AppScreen

    object NoteItem : AppNotesScreen(
        "app_notes_item_note",
        Icons.Default.Android
    ), AppScreen

    object Settings : AppNotesScreen(
        "app_notes_settings",
        Icons.Rounded.Settings
    ), AppScreen
}

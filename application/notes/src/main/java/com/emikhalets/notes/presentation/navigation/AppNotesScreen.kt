package com.emikhalets.notes.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.core.AppBottomBarItem

sealed class AppNotesScreen(val route: String, val icon: ImageVector) {

    object NotesList : AppNotesScreen(
        "app_notes_list",
        Icons.Rounded.EditNote
    ), AppBottomBarItem

    object NoteItem : AppNotesScreen(
        "app_note_item",
        Icons.Default.Android
    )

    object Settings : AppNotesScreen(
        "app_notes_settings",
        Icons.Rounded.Settings
    ), AppBottomBarItem
}

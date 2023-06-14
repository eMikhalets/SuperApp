package com.emikhalets.notes.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.core.AppBottomBarItem

sealed class NotesScreen(val route: String, val icon: ImageVector) {

    object Notes : NotesScreen(
        "app_notes_list",
        Icons.Rounded.EditNote
    ), AppBottomBarItem

    object Settings : NotesScreen(
        "app_notes_settings",
        Icons.Rounded.Settings
    ), AppBottomBarItem
}

package com.emikhalets.superapp

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.NoteAlt
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

enum class ApplicationType(@StringRes val nameRes: Int) {

    Convert(R.string.app_item_name_convert),
    Events(R.string.app_item_name_events),
    Finance(R.string.app_item_name_finance),
    Fitness(R.string.app_item_name_fitness),
    MediaLib(R.string.app_item_name_medialib),
    Notes(R.string.app_item_name_notes);

    companion object {

        fun getList(): List<ApplicationType> {
            return listOf(Convert, Events, Finance, Fitness, MediaLib, Notes)
        }
    }
}

@Composable
fun ApplicationType.getName(): String {
    return when (this) {
        ApplicationType.Convert -> stringResource(this.nameRes)
        ApplicationType.Events -> stringResource(this.nameRes)
        ApplicationType.Finance -> stringResource(this.nameRes)
        ApplicationType.Fitness -> stringResource(this.nameRes)
        ApplicationType.MediaLib -> stringResource(this.nameRes)
        ApplicationType.Notes -> stringResource(this.nameRes)
    }
}

@Composable
fun ApplicationType.getIcon(): ImageVector {
    return when (this) {
        ApplicationType.Convert -> Icons.Rounded.Repeat
        ApplicationType.Events -> Icons.Rounded.Event
        ApplicationType.Finance -> Icons.Rounded.MonetizationOn
        ApplicationType.Fitness -> Icons.Rounded.FitnessCenter
        ApplicationType.MediaLib -> Icons.Rounded.Bookmarks
        ApplicationType.Notes -> Icons.Rounded.NoteAlt
    }
}
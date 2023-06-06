package com.emikhalets.superapp.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppType(@StringRes val appNameRes: Int, val appIcon: ImageVector) {

    Events(
        appNameRes = com.emikhalets.common.R.string.app_events,
        appIcon = Icons.Rounded.Event
    ),
    Finances(
        appNameRes = com.emikhalets.common.R.string.app_finances,
        appIcon = Icons.Rounded.AccountBalanceWallet
    ),
    Fitness(
        appNameRes = com.emikhalets.common.R.string.app_fitness,
        appIcon = Icons.Rounded.FitnessCenter
    ),
    MediaLib(
        appNameRes = com.emikhalets.common.R.string.app_media_lib,
        appIcon = Icons.Rounded.AttachMoney
    ),
    Notes(
        appNameRes = com.emikhalets.common.R.string.app_notes,
        appIcon = Icons.Rounded.Notes
    );
}
package com.emikhalets.superapp.utils

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.superapp.R

enum class AppType(val appName: String, val appIcon: ImageVector) {

    Events(
        appName = Resources.getSystem().getString(R.string.app_events),
        appIcon = Icons.Rounded.Event
    ),
    Finances(
        appName = Resources.getSystem().getString(R.string.app_finances),
        appIcon = Icons.Rounded.AccountBalanceWallet
    ),
    Fitness(
        appName = Resources.getSystem().getString(R.string.app_fitness),
        appIcon = Icons.Rounded.FitnessCenter
    ),
    MediaLib(
        appName = Resources.getSystem().getString(R.string.app_media_lib),
        appIcon = Icons.Rounded.AttachMoney
    ),
    Notes(
        appName = Resources.getSystem().getString(R.string.app_notes),
        appIcon = Icons.Rounded.Notes
    );
}
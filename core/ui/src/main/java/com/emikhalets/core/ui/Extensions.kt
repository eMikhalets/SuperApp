package com.emikhalets.core.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.emikhalets.core.common.ApplicationEntity

@Composable
fun ApplicationEntity.getIcon(): ImageVector {
    return when (this) {
        ApplicationEntity.Events -> Icons.Rounded.Event
        ApplicationEntity.Finances -> Icons.Rounded.AccountBalanceWallet
        ApplicationEntity.Fitness -> Icons.Rounded.FitnessCenter
        ApplicationEntity.MediaLib -> Icons.Rounded.AttachMoney
        ApplicationEntity.Notes -> Icons.Rounded.Notes
    }
}

@Composable
fun ApplicationEntity.getName(): String {
    return stringResource(this.appNameRes)
}

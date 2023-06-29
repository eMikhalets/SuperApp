package com.emikhalets.core.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.core.common.ApplicationItem

fun ApplicationItem.getAppIcon(): ImageVector {
    return when (this) {
        ApplicationItem.Events -> Icons.Rounded.Event
        ApplicationItem.Finances -> Icons.Rounded.AccountBalanceWallet
        ApplicationItem.Fitness -> Icons.Rounded.FitnessCenter
        ApplicationItem.MediaLib -> Icons.Rounded.AttachMoney
        ApplicationItem.Notes -> Icons.Rounded.Notes
    }
}

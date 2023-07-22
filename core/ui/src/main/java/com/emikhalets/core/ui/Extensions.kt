package com.emikhalets.core.ui

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.core.common.R
import com.emikhalets.core.common.UiString

@Composable
fun ApplicationEntity.getIcon(): ImageVector {
    return when (this) {
        ApplicationEntity.Convert -> Icons.Rounded.Repeat
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

@Composable
fun UiString?.asString(): String = when (this) {
    is UiString.Message -> value ?: Resources.getSystem().getString(R.string.error_internal)
    is UiString.Resource -> stringResource(resId, *args)
    else -> Resources.getSystem().getString(R.string.error_internal)
}

@Composable
fun setExpandedIcon(expanded: Boolean) = if (expanded) {
    Icons.Rounded.KeyboardArrowDown
} else {
    Icons.Rounded.KeyboardArrowUp
}

@Preview(name = "Gray Theme", showBackground = true, showSystemUi = true)
annotation class ScreenPreview

@Preview(name = "Gray Theme", showBackground = true, showSystemUi = false)
annotation class BoxPreview
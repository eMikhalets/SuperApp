package com.emikhalets.core.ui.component

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import com.emikhalets.core.ui.theme.stroke

@Composable
@NonRestartableComposable
fun AppDivider() {
    Divider(
        color = MaterialTheme.colors.stroke
    )
}
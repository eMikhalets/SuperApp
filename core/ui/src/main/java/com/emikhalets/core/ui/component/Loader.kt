package com.emikhalets.core.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppLinearLoader(
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    if (visible) {
        LinearProgressIndicator(
            color = MaterialTheme.colors.primary,
            backgroundColor = Color.Transparent,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp)
        )
    } else {
        LinearProgressIndicator(
            progress = 0f,
            color = MaterialTheme.colors.primary,
            backgroundColor = Color.Transparent,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp)
        )
    }
}
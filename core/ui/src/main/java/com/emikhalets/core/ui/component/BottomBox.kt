package com.emikhalets.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.ScreenPreview
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun AppBottomBox(
    header: String = "",
    onDismiss: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary.copy(alpha = 0.5f))
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { onDismiss() }
            .padding(top = 40.dp)
    ) {
        AppCard(
            content = { content() },
            header = header,
            shape = RectangleShape,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@ScreenPreview
@Composable
private fun ScreenPreview() {
    AppTheme {
        AppBottomBox(onDismiss = {}) {
            Column(
                modifier = Modifier
                    .background(Color.Cyan)
                    .padding(16.dp)
            ) {
                AppTextField(
                    value = "asfdsgash",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
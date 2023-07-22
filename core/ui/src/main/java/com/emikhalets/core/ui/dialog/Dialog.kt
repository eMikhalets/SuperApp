package com.emikhalets.core.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.core.ui.ScreenPreview
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun AppDialog(
    header: String = "",
    onDismiss: () -> Unit = {},
    cancelable: Boolean = false,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    var maxDialogHeight by remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(bottom = 200.dp)
            .onSizeChanged { maxDialogHeight = with(density) { it.height.toDp() } }
    )

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = cancelable,
            dismissOnClickOutside = cancelable,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(0.dp, maxDialogHeight)
        ) {
            AppCard(
                content = { content() },
                header = header,
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AppDialogBottom(
    header: String = "",
    onDismiss: () -> Unit = {},
    cancelable: Boolean = false,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = cancelable,
            dismissOnClickOutside = cancelable,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
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
}

@ScreenPreview
@Composable
private fun ScreenPreview() {
    AppTheme {
        AppDialog(onDismiss = {}) {
            Column(modifier = Modifier.padding(16.dp)) {
                AppTextField(
                    value = "fdhsdhfd",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
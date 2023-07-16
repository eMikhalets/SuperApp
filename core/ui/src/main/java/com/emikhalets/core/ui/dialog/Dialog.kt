package com.emikhalets.core.ui.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AppDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    cancelable: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
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
            content = { content() },
            modifier = modifier
                .fillMaxSize()
        )
    }
}

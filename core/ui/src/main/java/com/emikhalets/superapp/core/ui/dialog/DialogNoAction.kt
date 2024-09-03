package com.emikhalets.superapp.core.ui.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.extentions.dialogBackground
import com.emikhalets.superapp.core.ui.theme.AppTheme

@Composable
fun DialogNoAction(
    onDismiss: () -> Unit = {},
    backClickDismiss: Boolean = false,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = backClickDismiss,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = Modifier.dialogBackground(),
            content = content
        )
    }
}

@ScreenPreview
@Composable
private fun ContentPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            DialogNoAction(
                content = {
                    Text(text = "first text")
                    Text(text = "second text")
                    Text(text = "third text")
                }
            )
        }
    }
}
package com.emikhalets.core.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.core.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.asString
import com.emikhalets.core.superapp.ui.component.AppTextButton
import com.emikhalets.core.superapp.ui.extentions.ScreenPreview
import com.emikhalets.core.superapp.ui.theme.AppTheme

@Composable
fun DialogMessage(
    message: String,
    title: String = "",
    onDismiss: () -> Unit = {},
) {
    DialogContent(message, title, onDismiss)
}

@Composable
fun DialogMessage(
    message: StringValue,
    title: String = "",
    onDismiss: () -> Unit = {},
) {
    val context = LocalContext.current
    DialogContent(message.asString(context), title, onDismiss)
}

@Composable
private fun DialogContent(
    message: String,
    title: String = "",
    onDismiss: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (title.isNotBlank()) {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = message,
                modifier = Modifier.fillMaxWidth()
            )
            AppTextButton(
                text = stringResource(R.string.core_ok),
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@ScreenPreview
@Composable
private fun ScreenPreview() {
    AppTheme {
        DialogMessage(
            message = "test message",
            title = "test title"
        )
    }
}
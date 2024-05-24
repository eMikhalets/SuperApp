package com.emikhalets.core.superapp.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.core.superapp.ui.component.AppTextButton
import com.emikhalets.core.superapp.ui.extentions.ScreenPreview
import com.emikhalets.core.superapp.ui.theme.AppTheme
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.StringValue.Companion.asString

@Composable
fun DialogOneBtn(
    message: String,
    title: String = "",
    onDismiss: () -> Unit = {},
) {
    DialogContent(message, title, onDismiss)
}

@Composable
fun DialogOneBtn(
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium,
                )
                .padding(12.dp, 16.dp, 12.dp, 8.dp)
        ) {
            if (title.isNotBlank()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            AppTextButton(
                text = stringResource(R.string.common_ok),
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
        DialogOneBtn(
            message = "test message",
            title = "test title"
        )
    }
}
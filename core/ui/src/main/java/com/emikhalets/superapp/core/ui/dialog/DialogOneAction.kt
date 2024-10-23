package com.emikhalets.superapp.core.ui.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.StringValue.Companion.asString
import com.emikhalets.superapp.core.ui.component.ButtonBorderless
import com.emikhalets.superapp.core.ui.component.TextHeader
import com.emikhalets.superapp.core.ui.component.TextPrimary
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.extentions.dialogBackground
import com.emikhalets.superapp.core.ui.theme.AppTheme

@Composable
fun DialogOneAction(
    message: StringValue,
    title: String = "",
    onConfirm: () -> Unit = {},
) {
    val context = LocalContext.current
    val content by remember { mutableStateOf(message.asString(context)) }
    DialogOneAction(
        message = content,
        title = title,
        onConfirm = onConfirm
    )
}

@Composable
fun DialogOneAction(
    onConfirm: () -> Unit = {},
    actionText: String = "",
    backClickDismiss: Boolean = false,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onConfirm() },
        properties = DialogProperties(
            dismissOnBackPress = backClickDismiss,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(modifier = Modifier.dialogBackground()) {
            content()
            ButtonBorderless(
                text = actionText.ifBlank { stringResource(R.string.ok) },
                onClick = onConfirm,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.End)
            )
        }
    }
}

@Composable
fun DialogOneAction(
    message: String,
    title: String = "",
    actionText: String = "",
    onConfirm: () -> Unit = {},
) {
    AlertDialog(
        text = { TextPrimary(message) },
        title = if (title.isNotBlank()) {
            { TextHeader(title) }
        } else null,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        onDismissRequest = { onConfirm() },
        confirmButton = {
            ButtonBorderless(
                text = actionText.ifBlank { stringResource(R.string.ok) },
                onClick = onConfirm,
            )
        }
    )
}

@ScreenPreview
@Composable
private fun TextPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            DialogOneAction(
                title = "test title",
                message = "test message",
            )
        }
    }
}

@ScreenPreview
@Composable
private fun ContentPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            DialogOneAction(
                content = {
                    Text(text = "first text")
                    Text(text = "second text")
                    Text(text = "third text")
                }
            )
        }
    }
}
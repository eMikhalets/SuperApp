package com.emikhalets.superapp.core.ui.dialog

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.StringValue.Companion.asString
import com.emikhalets.superapp.core.ui.component.AppTextButton
import com.emikhalets.superapp.core.ui.component.AppTextHeader
import com.emikhalets.superapp.core.ui.component.AppTextPrimary
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.theme.AppTheme

@Composable
fun DialogOneAction(
    message: StringValue,
    title: String = "",
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    val context = LocalContext.current
    val content by remember { mutableStateOf(message.asString(context)) }
    DialogOneAction(
        message = content,
        title = title,
        onDismiss = onDismiss,
        onConfirm = onConfirm
    )
}

@Composable
fun DialogOneAction(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    actionText: String = "",
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.large,
                )
                .clip(MaterialTheme.shapes.large)
                .padding(24.dp)
        ) {
            content()
            AppTextButton(
                text = actionText.ifBlank { stringResource(R.string.common_ok) },
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
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    AlertDialog(
        text = { AppTextPrimary(message) },
        title = if (title.isNotBlank()) {
            { AppTextHeader(title) }
        } else null,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            AppTextButton(
                text = actionText.ifBlank { stringResource(R.string.common_ok) },
                onClick = onDismiss,
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
                message = "test message",
                title = "test title"
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
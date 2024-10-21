package com.emikhalets.superapp.core.ui.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
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
fun DialogTwoAction(
    message: StringValue,
    title: String = "",
    leftText: String = "",
    rightText: String = "",
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val content by remember { mutableStateOf(message.asString(context)) }
    DialogTwoAction(
        message = content,
        title = title,
        leftText = leftText,
        rightText = rightText,
        onLeftClick = onLeftClick,
        onRightClick = onRightClick,
    )
}

@Composable
fun DialogTwoAction(
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    leftText: String = "",
    rightText: String = "",
    dismissOnBackPress: Boolean = false,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onLeftClick() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = dismissOnBackPress,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(modifier = Modifier.dialogBackground()) {
            content()
            Row(modifier = Modifier.align(Alignment.End)) {
                ButtonBorderless(
                    text = leftText.ifBlank { stringResource(R.string.cancel) },
                    onClick = onLeftClick,
                    modifier = Modifier.padding(top = 24.dp, end = 8.dp)
                )
                ButtonBorderless(
                    text = rightText.ifBlank { stringResource(R.string.ok) },
                    onClick = onRightClick,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
    }
}

@Composable
fun DialogTwoAction(
    message: String,
    title: String = "",
    leftText: String = "",
    rightText: String = "",
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
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
        onDismissRequest = { onLeftClick() },
        dismissButton = {
            ButtonBorderless(
                text = leftText.ifBlank { stringResource(R.string.cancel) },
                onClick = onLeftClick,
            )
        },
        confirmButton = {
            ButtonBorderless(
                text = rightText.ifBlank { stringResource(R.string.ok) },
                onClick = onRightClick,
            )
        }
    )
}

@ScreenPreview
@Composable
private fun TextPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            DialogTwoAction(
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
            DialogTwoAction(
                content = {
                    Text(text = "first text")
                    Text(text = "second text")
                    Text(text = "third text")
                }
            )
        }
    }
}
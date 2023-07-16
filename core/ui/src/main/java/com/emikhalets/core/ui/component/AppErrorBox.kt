package com.emikhalets.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.BoxPreview
import com.emikhalets.core.ui.asString
import com.emikhalets.core.ui.dialog.AppDialog
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.text

// TODO: animate error box
@Composable
fun AppErrorBox(
    message: UiString?,
    modifier: Modifier = Modifier,
    hasBorder: Boolean = true,
    onDismiss: () -> Unit = {},
) {
    if (message == null) return

    logi("ErrorBox", "Invoke: message = ${message.asString()}")

    AppDialog(onDismiss = onDismiss) {
        AppCard(
            shape = RectangleShape,
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = message.asString(),
                style = MaterialTheme.typography.text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            AppTextButton(
                text = stringResource(com.emikhalets.core.common.R.string.app_ok),
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            )
        }
    }
}

@BoxPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            AppErrorBox(
                message = UiString.create("Card error text message bla bla bla."),
                modifier = Modifier
            )
        }
    }
}

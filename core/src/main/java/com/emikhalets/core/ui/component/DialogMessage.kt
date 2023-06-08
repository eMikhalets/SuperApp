package com.emikhalets.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.emikhalets.core.R
import com.emikhalets.core.ui.UiString
import com.emikhalets.core.ui.asString
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun AppMessageDialog(
    message: UiString?,
    onDismiss: () -> Unit = {},
    title: String? = null,
) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            if (!title.isNullOrBlank()) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            Text(
                text = message.asString(context),
                style = MaterialTheme.typography.body1,
                modifier = if (!title.isNullOrBlank()) {
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            Divider(modifier = Modifier.padding(16.dp))
            AppTextButton(
                text = stringResource(R.string.app_save),
                onClick = { onDismiss() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        AppMessageDialog(UiString.create("Dialog message"), {}, "Dialog title")
    }
}
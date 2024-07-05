package com.emikhalets.superapp.feature.convert.ui.currencies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.superapp.core.ui.extentions.BoxPreview
import com.emikhalets.superapp.core.ui.extentions.clickableOnce
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.feature.convert.R

@Composable
fun NewCurrencyDialog(
    code: String,
    onCodeChanged: (String) -> Unit,
    onSaveClick: (String) -> Unit,
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium,
                )
                .padding(16.dp)
                .height(IntrinsicSize.Min)
        ) {
            OutlinedTextField(
                value = code,
                onValueChange = onCodeChanged,
                placeholder = { Text(stringResource(R.string.convert_code_hint)) },
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = { onSaveClick(code) }),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.Rounded.Save,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickableOnce { onSaveClick(code) }
                    .padding(12.dp)
            )
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickableOnce { onDismiss() }
                    .padding(12.dp)
            )
        }
    }
}

@BoxPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            NewCurrencyDialog(
                code = "USD",
                onCodeChanged = {},
                onSaveClick = {},
            )
        }
    }
}
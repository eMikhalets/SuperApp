package com.emikhalets.convert.presentation.screens.currencies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.convert.domain.R
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.BoxPreview
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.dialog.AppDialog
import com.emikhalets.core.ui.theme.AppTheme

// TODO: animate new currency box
@Composable
fun NewCurrencyBox(
    newCurrencyCode: String,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    onNewCurrencyEvent: (String, Boolean) -> Unit,
    onSaveClick: (String) -> Unit,
    onDismiss: () -> Unit = {},
) {
    if (!isVisible) return

    logi("NewCurrencyBox", "Invoke: message = $newCurrencyCode")

    val focusRequester = remember { FocusRequester() }

    AppDialog(onDismiss = onDismiss) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppTextField(
                value = newCurrencyCode,
                onValueChange = { onNewCurrencyEvent(it, true) },
                placeholder = stringResource(R.string.app_convert_code_help),
                padding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            Icon(
                imageVector = Icons.Rounded.Save,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
                    .clickable { onSaveClick(newCurrencyCode) }
            )
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
                    .clickable { onNewCurrencyEvent("", false) }
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@BoxPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            NewCurrencyBox(
                newCurrencyCode = "usd",
                isVisible = true,
                onNewCurrencyEvent = { _, _ -> },
                onSaveClick = {},
                modifier = Modifier
            )
        }
    }
}

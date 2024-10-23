package com.emikhalets.superapp.feature.convert.ui.currencies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.ui.component.TextFieldPrimary
import com.emikhalets.superapp.core.ui.component.TextPrimary
import com.emikhalets.superapp.core.ui.dialog.DialogNoAction
import com.emikhalets.superapp.core.ui.extentions.BoxPreview
import com.emikhalets.superapp.core.ui.extentions.clickableOnce
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.feature.convert.R

@Composable
fun NewCurrencyDialog(
    code: String,
    onCodeChanged: (String) -> Unit,
    onSaveClick: (String) -> Unit,
    onCancelClick: () -> Unit = {},
) {
    DialogNoAction(
        onDismiss = onCancelClick
    ) {
        TextPrimary(
            text = stringResource(R.string.convert_new_currency),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium,
                )
                .height(IntrinsicSize.Min)
        ) {
            TextFieldPrimary(
                value = code,
                onValueChange = onCodeChanged,
                placeholder = stringResource(R.string.convert_code_hint),
                singleLine = true,
                actions = KeyboardActions(onDone = { onSaveClick(code) }),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag("currencyCodeTextField")
            )
            Icon(
                imageVector = Icons.Rounded.Save,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickableOnce { onSaveClick(code) }
                    .testTag("currencyCodeSaveIcon")
            )
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickableOnce { onCancelClick() }
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

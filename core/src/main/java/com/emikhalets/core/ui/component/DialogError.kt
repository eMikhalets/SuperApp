package com.emikhalets.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.core.R
import com.emikhalets.core.ui.StringValue
import com.emikhalets.core.ui.asString
import com.emikhalets.core.ui.extentions.BoxPreview
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun DialogError(
    message: StringValue?,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
) {
    if (message == null) return
    AppBottomDialog(
        header = stringResource(R.string.core_common_error),
        cancelable = true,
        onDismiss = onDismiss
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = message.asString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            AppTextButton(
                text = stringResource(R.string.core_common_ok),
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
            DialogError(
                message = StringValue.create("Card error text message bla bla bla."),
                modifier = Modifier
            )
        }
    }
}

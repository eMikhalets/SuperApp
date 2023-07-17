package com.emikhalets.core.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.R
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.component.AppTextButton
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.text

@Composable
fun <T> AppDialogDelete(
    entity: T?,
    onDeleteClick: (entity: T?) -> Unit,
    onDismiss: () -> Unit = {},
) {
    if (entity == null) return

    logi("DeleteDialog", "Invoke: entity = $entity")

    AppDialog(
        header = stringResource(R.string.app_dialog_delete_title),
        onDismiss = onDismiss
    ) {
        Text(
            text = stringResource(R.string.app_dialog_delete_message),
            style = MaterialTheme.typography.text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppTextButton(
                text = stringResource(R.string.app_cancel),
                bold = true,
                onClick = { onDismiss() },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            )
            AppTextButton(
                text = stringResource(R.string.app_delete),
                onClick = { onDeleteClick(entity) },
                modifier = Modifier
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
        AppDialogDelete(
            entity = null,
            onDeleteClick = {},
            onDismiss = {}
        )
    }
}
package com.emikhalets.superapp.feature.salary.ui.chart

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.emikhalets.superapp.core.ui.dialog.DialogOneAction
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.feature.salary.R
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import com.emikhalets.superapp.feature.salary.domain.SalaryType
import java.util.Date

@Composable
internal fun SalaryEditDialog(
    model: SalaryModel?,
    onSaveClick: (SalaryModel) -> Unit,
    onDismiss: () -> Unit = {},
) {
    model ?: return

    var value by remember { mutableLongStateOf(model.value) }

    DialogOneAction(
        actionText = stringResource(R.string.salary_app_save),
        onDismiss = onDismiss,
        onConfirm = { onSaveClick(model.copy(value = value)) }
    ) {
        OutlinedTextField(
            value = value.toString(),
            onValueChange = { value = it.toLongOrNull() ?: 0 },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        SalaryEditDialog(
            model = SalaryModel(
                id = 1,
                value = 0,
                timestamp = Date().time,
                type = SalaryType.SALARY,
            ),
            onDismiss = {},
            onSaveClick = {}
        )
    }
}

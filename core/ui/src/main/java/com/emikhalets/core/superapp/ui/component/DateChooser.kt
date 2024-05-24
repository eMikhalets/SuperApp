package com.emikhalets.core.superapp.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.core.superapp.ui.theme.AppTheme
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.helper.DateHelper
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateChooser(
    timestamp: Long,
    showDialog: Boolean,
    onSelect: (Long?) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = onCancel,
            confirmButton = {
                AppTextButton(
                    text = stringResource(R.string.common_select),
                    onClick = { onSelect(datePickerState.selectedDateMillis) },
                    enabled = confirmEnabled
                )
            },
            dismissButton = {
                AppTextButton(
                    text = stringResource(R.string.common_cancel),
                    onClick = onCancel
                )
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    OutlinedTextField(
        value = DateHelper.formatDate(datePickerState.selectedDateMillis, "dd.MM.yyyy"),
        onValueChange = {},
        readOnly = true,
        singleLine = true,
        leadingIcon = { Icon(Icons.Rounded.Update, contentDescription = null) },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SpinnerPreview() {
    AppTheme {
        AppDateChooser(
            timestamp = Date().time,
            showDialog = false,
            onSelect = {},
            onCancel = {},
        )
    }
}
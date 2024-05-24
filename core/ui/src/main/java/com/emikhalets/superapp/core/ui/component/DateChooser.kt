package com.emikhalets.superapp.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.ui.extentions.BoxPreview
import com.emikhalets.superapp.core.ui.extentions.clickableOnce
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.date.DateHelper
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateChooser(
    timestamp: Long,
    onSelect: (Long?) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    initialDialogVisible: Boolean = false,
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
    var isDialogVisible by remember { mutableStateOf(initialDialogVisible) }

    if (isDialogVisible) {
        DatePickerDialog(
            onDismissRequest = onCancel,
            confirmButton = {
                AppTextButton(
                    text = stringResource(R.string.common_select),
                    enabled = confirmEnabled,
                    onClick = {
                        onSelect(datePickerState.selectedDateMillis)
                        isDialogVisible = false
                    },
                )
            },
            dismissButton = {
                AppTextButton(
                    text = stringResource(R.string.common_cancel),
                    onClick = {
                        onCancel()
                        isDialogVisible = false
                    },
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
        leadingIcon = { Icon(Icons.Rounded.CalendarMonth, contentDescription = null) },
        modifier = modifier.clickableOnce { isDialogVisible = true }
    )
}

@BoxPreview
@Composable
private fun SpinnerPreview() {
    AppTheme {
        AppDateChooser(
            timestamp = Date().time,
            onSelect = {},
            onCancel = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@BoxPreview
@Composable
private fun SpinnerDatePreview() {
    AppTheme {
        AppDateChooser(
            timestamp = Date().time,
            onSelect = {},
            onCancel = {},
            initialDialogVisible = true,
            modifier = Modifier.padding(8.dp)
        )
    }
}
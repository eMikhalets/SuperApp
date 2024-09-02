package com.emikhalets.superapp.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.format
import com.emikhalets.superapp.core.ui.extentions.BoxPreview
import com.emikhalets.superapp.core.ui.extentions.clickableOnce
import com.emikhalets.superapp.core.ui.theme.AppTheme
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateChooser(
    timestamp: Long,
    onSelect: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit = {},
    initialDialogVisible: Boolean = false,
) {
    val state = rememberDatePickerState()
    var isDialogVisible by remember { mutableStateOf(initialDialogVisible) }
    val confirmEnabled by remember {
        derivedStateOf {
            val time = state.selectedDateMillis
            time != null && time != 0L
        }
    }

    if (isDialogVisible) {
        DatePickerDialog(
            onDismissRequest = onCancel,
            confirmButton = {
                ButtonBorderless(
                    text = stringResource(R.string.select),
                    enabled = confirmEnabled,
                    onClick = {
                        onSelect(state.selectedDateMillis ?: 0)
                        isDialogVisible = false
                    },
                )
            },
            dismissButton = {
                ButtonBorderless(
                    text = stringResource(R.string.cancel),
                    onClick = {
                        onCancel()
                        isDialogVisible = false
                    },
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            DatePicker(state = state)
        }
    }

    OutlinedTextField(
        value = timestamp.format("dd.MM.yyyy") ?: "null date",
        onValueChange = {},
        readOnly = true,
        singleLine = true,
        enabled = false,
        leadingIcon = { Icon(Icons.Rounded.CalendarMonth, contentDescription = null) },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
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
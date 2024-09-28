package com.emikhalets.superapp.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.core.ui.theme.textField

@Composable
fun TextFieldPrimary(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String = "",
    isError: Boolean = false,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    ignoreNonEnabledColors: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    trailingIconClick: (() -> Unit)? = null,
    actions: KeyboardActions = KeyboardActions.Default,
    options: KeyboardOptions = KeyboardOptions.Default,
    shape: Shape = MaterialTheme.shapes.textField,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = if (placeholder.isNotBlank()) {
            { Text(placeholder) }
        } else null,
        label = if (label.isNotBlank()) {
            { Text(label) }
        } else null,
        leadingIcon = if (leadingIcon != null) {
            { Icon(imageVector = leadingIcon, contentDescription = null) }
        } else null,
        trailingIcon = if (trailingIcon != null) {
            { Icon(imageVector = trailingIcon, contentDescription = null) }
        } else null,
        singleLine = singleLine,
        isError = isError,
        readOnly = readOnly,
        enabled = enabled,
        keyboardActions = actions,
        keyboardOptions = options,
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
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
        modifier = modifier
    )
}

@Composable
fun TextFieldBorderless(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String = "",
    isError: Boolean = false,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    ignoreNonEnabledColors: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    trailingIconClick: (() -> Unit)? = null,
    actions: KeyboardActions = KeyboardActions.Default,
    options: KeyboardOptions = KeyboardOptions.Default,
    shape: Shape = MaterialTheme.shapes.textField,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = if (placeholder.isNotBlank()) {
            { Text(placeholder) }
        } else null,
        label = if (label.isNotBlank()) {
            { Text(label) }
        } else null,
        leadingIcon = if (leadingIcon != null) {
            { Icon(imageVector = leadingIcon, contentDescription = null) }
        } else null,
        trailingIcon = if (trailingIcon != null) {
            { Icon(imageVector = trailingIcon, contentDescription = null) }
        } else null,
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        isError = isError,
        readOnly = readOnly,
        enabled = enabled,
        keyboardActions = actions,
        keyboardOptions = options,
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
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
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        Column(Modifier.fillMaxWidth()) {
            TextFieldPrimary(
                value = "Text Header",
                onValueChange = {},
                modifier = Modifier.padding(8.dp)
            )
            TextFieldPrimary(
                value = "",
                placeholder = "Text Header Long Long Long Long Long Long Long",
                singleLine = true,
                onValueChange = {},
                modifier = Modifier.padding(8.dp)
            )
            TextFieldBorderless(
                value = "Text Header",
                onValueChange = {},
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
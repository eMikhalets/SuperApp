package com.emikhalets.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.ui.BoxPreview
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.textSub

@OptIn(ExperimentalMaterialApi::class)
@Composable
@NonRestartableComposable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
        .copy(capitalization = KeyboardCapitalization.Sentences),
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    shape: Shape = RectangleShape,
    fontSize: TextUnit = 16.sp,
    textColor: Color = MaterialTheme.colors.onSurface,
    padding: PaddingValues = PaddingValues(0.dp),
    background: Color = Color.Transparent,
) {
    val isError = errorMessage != null
    val interactionSource = remember { MutableInteractionSource() }
    val maxLines = if (singleLine) Int.MAX_VALUE else 1

    val colors = TextFieldDefaults.textFieldColors(
        textColor = textColor,
        disabledTextColor = MaterialTheme.colors.secondary,
        backgroundColor = background,
        cursorColor = MaterialTheme.colors.onSurface,
        errorCursorColor = MaterialTheme.colors.onSurface,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        leadingIconColor = MaterialTheme.colors.onSurface,
        disabledLeadingIconColor = MaterialTheme.colors.secondary,
        errorLeadingIconColor = MaterialTheme.colors.onSurface,
        trailingIconColor = MaterialTheme.colors.onSurface,
        disabledTrailingIconColor = MaterialTheme.colors.onSurface,
        errorTrailingIconColor = MaterialTheme.colors.onSurface,
        placeholderColor = MaterialTheme.colors.secondary,
        disabledPlaceholderColor = MaterialTheme.colors.secondary
    )

    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = LocalTextStyle.current.copy(fontSize = fontSize, color = textColor),
            enabled = enabled,
            readOnly = readOnly,
            cursorBrush = SolidColor(colors.cursorColor(isError).value),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = 1,
            modifier = modifier
                .fillMaxWidth()
                .background(background, shape),
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    contentPadding = if (leadingIcon != null || trailingIcon != null) {
                        PaddingValues(16.dp)
                    } else {
                        padding
                    },
                    placeholder = if (!placeholder.isNullOrBlank()) {
                        { Text(placeholder, fontSize = fontSize) }
                    } else null,
                    leadingIcon = if (leadingIcon != null) {
                        { Icon(leadingIcon, contentDescription = null) }
                    } else null,
                    trailingIcon = if (trailingIcon != null) {
                        { Icon(trailingIcon, contentDescription = null) }
                    } else null,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                )
            }
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.textSub,
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 0.dp, bottom = 4.dp)
            )
        }
    }
}

@BoxPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            AppTextField(
                value = "Test text",
                onValueChange = {},
                modifier = Modifier.background(Color.LightGray)
            )
            AppTextField(
                value = "",
                onValueChange = {},
                placeholder = "Test placeholder",
                modifier = Modifier.background(Color.Gray)
            )
            AppTextField(
                value = "Test text",
                onValueChange = {},
                leadingIcon = Icons.Default.Android,
                trailingIcon = Icons.Default.Android,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )
            AppTextField(
                value = "Test text",
                onValueChange = {},
                leadingIcon = Icons.Default.Android,
                errorMessage = "Test error Test error Test error Test error Test error",
                modifier = Modifier.background(Color.Gray)
            )
            AppTextField(
                value = "Test Height",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Green)
            )
            AppTextField(
                value = "Test text",
                onValueChange = {},
                leadingIcon = Icons.Default.Android,
                trailingIcon = Icons.Default.Android,
                fontSize = 24.sp,
                textColor = Color.Blue,
                modifier = Modifier.background(Color.LightGray)
            )
            Row(Modifier.fillMaxWidth()) {
                AppTextField(
                    value = "123.45",
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(32.dp)
                )
            }
        }
    }
}
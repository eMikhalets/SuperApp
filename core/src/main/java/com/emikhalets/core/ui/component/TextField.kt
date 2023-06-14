package com.emikhalets.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
@NonRestartableComposable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    placeholder: String? = null,
    label: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    keyboardType: KeyboardType = KeyboardType.Text,
    onDoneClick: (KeyboardActionScope.() -> Unit)? = {},
    textColor: Color = MaterialTheme.colors.onSurface,
    fontSize: TextUnit = 16.sp,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val maxLinesValue by remember { mutableStateOf(if (maxLines < 1) 1 else maxLines) }
    val singleLineValue by remember { mutableStateOf(maxLinesValue <= 1) }

    val colors = TextFieldDefaults.textFieldColors(
        backgroundColor = MaterialTheme.colors.surface,
        textColor = textColor,
        cursorColor = MaterialTheme.colors.primary,
        errorCursorColor = MaterialTheme.colors.error,
    )

    Column(modifier = modifier) {
        BasicTextField(
            value = value,
            modifier = modifier
                .background(MaterialTheme.colors.surface, RectangleShape)
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                    minHeight = TextFieldDefaults.MinHeight
                ),
            onValueChange = onValueChange,
            enabled = true,
            readOnly = false,
            textStyle = LocalTextStyle.current.copy(fontSize = fontSize),
            cursorBrush = SolidColor(colors.cursorColor(errorMessage != null).value),
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                capitalization = capitalization,
                keyboardType = keyboardType,
            ),
            keyboardActions = KeyboardActions(
                onDone = onDoneClick,
            ),
            interactionSource = interactionSource,
            singleLine = singleLineValue,
            maxLines = maxLinesValue,
            minLines = 1,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = value,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    singleLine = singleLineValue,
                    enabled = true,
                    isError = errorMessage != null,
                    interactionSource = interactionSource,
                    colors = colors
                )
            }
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppTextField("Test text", {}, Modifier.padding(8.dp))
    }
}
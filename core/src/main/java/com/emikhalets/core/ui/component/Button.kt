package com.emikhalets.core.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text = text)
    }
}

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonPreview() {
    AppTheme {
        AppButton(text = "Some text", onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun TextButtonPreview() {
    AppTheme {
        AppTextButton(text = "Some text", onClick = {})
    }
}

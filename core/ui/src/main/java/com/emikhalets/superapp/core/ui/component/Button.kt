package com.emikhalets.superapp.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.ui.extentions.BoxPreview
import com.emikhalets.superapp.core.ui.theme.AppTheme

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        modifier = modifier.defaultMinSize(minHeight = 52.dp)
    ) {
        AppTextPrimary(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun AppButtonTopIcon(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            AppTextPrimary(
                text = text,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        modifier = modifier.defaultMinSize(minHeight = 52.dp)
    ) {
        AppTextButton(text = text)
    }
}

@BoxPreview
@Composable
private fun ButtonPreview() {
    AppTheme {
        AppButton(
            text = "Some text",
            onClick = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@BoxPreview
@Composable
private fun AppButtonTopIconPreview() {
    AppTheme {
        AppButtonTopIcon(
            text = "Some text",
            icon = Icons.Rounded.Update,
            onClick = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@BoxPreview
@Composable
private fun TextButtonPreview() {
    AppTheme {
        AppTextButton(
            text = "Some text",
            onClick = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}

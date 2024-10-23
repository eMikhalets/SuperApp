package com.emikhalets.superapp.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.ui.extentions.BoxPreview
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.core.ui.theme.button

@Composable
fun ButtonPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.button,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier.defaultMinSize(minHeight = 52.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun ButtonPrimaryTopIcon(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.button,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier.defaultMinSize(minHeight = 52.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Text(
                text = text,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun ButtonBorderless(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.button,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.defaultMinSize(minHeight = 52.dp)
    ) {
        Text(text = text)
    }
}

@BoxPreview
@Composable
private fun ButtonPreview() {
    AppTheme {
        Column {
            ButtonPrimary(
                text = "Some text",
                onClick = {},
                modifier = Modifier.padding(8.dp)
            )
            ButtonPrimaryTopIcon(
                text = "Some text",
                icon = Icons.Rounded.Update,
                onClick = {},
                modifier = Modifier.padding(8.dp)
            )
            ButtonBorderless(
                text = "Some text",
                onClick = {},
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
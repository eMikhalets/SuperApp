package com.emikhalets.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.theme.AppTheme

@Composable
@NonRestartableComposable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            modifier = Modifier.padding(10.dp, 4.dp)
        )
    }
}

@Composable
@NonRestartableComposable
fun AppButtonOk(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppButton(text = stringResource(com.emikhalets.core.common.R.string.app_ok), onClick, modifier)
}

@Composable
@NonRestartableComposable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    bold: Boolean = false,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(10.dp, 4.dp)
        )
    }
}

@Composable
@NonRestartableComposable
fun BoxScope.AppFloatButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = { onClick() },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
@NonRestartableComposable
fun AppFloatButtonBox(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()
        AppFloatButton(icon, onClick)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppButton("Test text", {}, Modifier.padding(8.dp))
        AppTextButton("Test text", {}, Modifier.padding(8.dp))
    }
}
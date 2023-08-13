package com.emikhalets.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = if (onBackClick != null) {
            { AppTopBarIcon(onBackClick) }
        } else null,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun AppTopBarIcon(onBackClick: () -> Unit) {
    Icon(
        imageVector = Icons.Rounded.ArrowBack,
        contentDescription = null,
        modifier = Modifier.clickable { onBackClick() }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppTopBar(
            title = "Test title",
            modifier = Modifier.padding(16.dp)
        )
    }
}

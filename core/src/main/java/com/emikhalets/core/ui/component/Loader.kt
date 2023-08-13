package com.emikhalets.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun LinearLoader(
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    LinearProgressIndicator(modifier = modifier)
}

@Preview(showBackground = true)
@Composable
private fun LinearLoaderPreview() {
    AppTheme {
        LinearLoader(true, Modifier.padding(8.dp))
    }
}

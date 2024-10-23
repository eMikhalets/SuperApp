package com.emikhalets.superapp.core.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.ui.extentions.BoxPreview
import com.emikhalets.superapp.core.ui.theme.AppTheme

@Composable
fun LinearLoader(
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    if (visible) {
        LinearProgressIndicator(modifier = modifier.fillMaxWidth())
    }
}

@BoxPreview
@Composable
private fun LinearLoaderPreview() {
    AppTheme {
        LinearLoader(
            visible = true,
            modifier = Modifier.padding(8.dp)
        )
    }
}

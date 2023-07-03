package com.emikhalets.notes.presentation.screens.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.ApplicationItem.Notes.appNameRes
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.theme.AppTheme

private const val TAG = "Settings"

@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
) {
    logi(TAG, "Invoke")
    ScreenContent(
        onBackClick = navigateBack
    )
}

@Composable
private fun ScreenContent(
    onBackClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke")
    AppChildScreenBox(onBackClick, stringResource(appNameRes)) {
        Text(
            text = "Not implemented",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            onBackClick = {}
        )
    }
}

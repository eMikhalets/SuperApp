package com.emikhalets.notes.presentation.screens.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.ApplicationEntity
import com.emikhalets.core.ui.ScreenPreview
import com.emikhalets.core.ui.component.AppContent
import com.emikhalets.core.ui.getName
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
    AppContent(ApplicationEntity.Notes.getName(), onBackClick) {
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

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            onBackClick = {}
        )
    }
}

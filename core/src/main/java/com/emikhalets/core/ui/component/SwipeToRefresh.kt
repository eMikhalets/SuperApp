package com.emikhalets.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.emikhalets.core.ui.extentions.ScreenPreview
import com.emikhalets.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppSwipeToRefresh(
    isLoading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = onRefresh)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        PullRefreshIndicator(
            state = pullRefreshState,
            refreshing = isLoading,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        LinearLoader(visible = isLoading)
        Column(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        AppSwipeToRefresh(
            isLoading = true,
            onRefresh = {},
        ) {
            Text(
                text = "Test content",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

package com.emikhalets.core.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun ChildScreenColumn(
    @StringRes titleRes: Int,
    onBackClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    ChildScreenColumn(stringResource(titleRes), onBackClick, content)
}

@Composable
fun ChildScreenColumn(
    title: String,
    onBackClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(title, onBackClick = onBackClick)
        content()
    }
}
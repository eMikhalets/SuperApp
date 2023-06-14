package com.emikhalets.core.ui.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun AppChildScreenBox(
    onBackClick: () -> Unit,
    label: String? = null,
    isScrollable: Boolean = false,
    content: @Composable ColumnScope.(PaddingValues) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AppTopBar(onBackClick, Modifier, label)
        Column(
            content = { content(PaddingValues(top = 52.dp)) },
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .verticalScrollIf(isScrollable, rememberScrollState())
                .padding(top = 52.dp)
        )
    }
}

private fun Modifier.verticalScrollIf(condition: Boolean, scrollState: ScrollState): Modifier {
    return if (condition) verticalScroll(scrollState) else this
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppChildScreenBox({}) {
            repeat(50) {
                Text(text = "Text text text", fontSize = 20.sp)
            }
        }
    }
}
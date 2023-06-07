package com.emikhalets.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.ui.theme.AppTheme

@Composable
fun ChildScreenBox(
    onBackClick: () -> Unit,
    label: String? = null,
    enableScrolling: Boolean = false,
    content: @Composable (PaddingValues) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AppTopBar(onBackClick, Modifier, label)
        Column(
            content = { content(PaddingValues(top = 52.dp)) },
            modifier = Modifier
                .fillMaxSize()
                .setScrolling(enableScrolling)
                .padding(top = 52.dp)
        )
    }
}

private fun Modifier.setScrolling(enable: Boolean): Modifier = composed {
    if (enable) this.verticalScroll(rememberScrollState())
    else this
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ChildScreenBox(
            onBackClick = {},
            label = "Test label",
            enableScrolling = false
        ) {
            repeat(50) {
                Text(text = it.toString(), fontSize = 20.sp)
            }
        }
    }
}
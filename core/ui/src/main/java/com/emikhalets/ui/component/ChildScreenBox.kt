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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.ui.theme.AppTheme

val CHILD_SCREEN_BOX_PADDING = 52.dp

@Composable
fun ChildScreenBox(
    onBackClick: () -> Unit,
    label: String? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AppTopBar(onBackClick, Modifier, label)
        content(PaddingValues(top = 52.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ChildScreenBox({}) {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
            ) {
                repeat(50) {
                    Text(text = it.toString(), fontSize = 20.sp)
                }
            }
        }
    }
}
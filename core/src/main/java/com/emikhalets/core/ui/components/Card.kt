package com.emikhalets.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppHeaderCardColumn(
    header: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(modifier = modifier) {
        Column {
            Text(
                text = header,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            Divider(modifier = Modifier.padding(8.dp))
            content()
        }
    }
}

@Composable
fun AppHeaderCardRow(
    header: String,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Card(modifier = modifier) {
        Column {
            Text(
                text = header,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            Divider(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.width(IntrinsicSize.Max),
                content = { content() }
            )
        }
    }
}
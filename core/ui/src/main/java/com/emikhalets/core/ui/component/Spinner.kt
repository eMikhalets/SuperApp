package com.emikhalets.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.text

@Composable
fun <T> AppSpinner(
    items: List<T>,
    onSelect: (T) -> Unit,
    nameGetter: @Composable (T) -> String,
    modifier: Modifier = Modifier,
    selected: T = items.first(),
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = nameGetter(selected),
            style = MaterialTheme.typography.text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onSelect(item)
                    }
                ) {
                    Text(
                        text = nameGetter(item),
                        style = MaterialTheme.typography.text
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppSpinner(
            items = listOf("1", "2", "3", "4", "5"),
            selected = "2",
            nameGetter = { it },
            onSelect = {},
            modifier = Modifier.padding(32.dp)
        )
    }
}

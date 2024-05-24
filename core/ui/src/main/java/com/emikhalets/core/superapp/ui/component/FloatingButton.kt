package com.emikhalets.core.superapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emikhalets.core.superapp.ui.extentions.BoxPreview
import com.emikhalets.core.superapp.ui.theme.AppTheme
import com.emikhalets.core.superapp.ui.theme.circle

@Composable
fun AppFloatingButtonBox(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()
        FloatingActionButton(
            onClick = onClick,
            shape = MaterialTheme.shapes.circle,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
        }
    }
}

@BoxPreview
@Composable
private fun TextButtonPreview() {
    AppTheme {
        AppFloatingButtonBox(
            onClick = {},
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "some text")
        }
    }
}
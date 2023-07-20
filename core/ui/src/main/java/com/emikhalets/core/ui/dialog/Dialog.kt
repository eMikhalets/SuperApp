package com.emikhalets.core.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.core.ui.component.AppCard

@Composable
fun AppDialog(
    modifier: Modifier = Modifier,
    header: String = "",
    onDismiss: () -> Unit = {},
    cancelable: Boolean = false,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = cancelable,
            dismissOnClickOutside = cancelable,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable { onDismiss() }
        ) {
            AppCard(
                content = { content() },
                header = header,
                shape = RectangleShape,
                modifier = modifier
                    .padding(top = 80.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clickable {}
            )
        }
    }
}

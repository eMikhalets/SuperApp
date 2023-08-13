package com.emikhalets.core.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.StringEmpty
import com.emikhalets.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppSwipeToDelete(
    onLeftSwiped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onLeftSwiped()
                return@rememberDismissState true
            }
            false
        }
    )
    SwipeToDismiss(
        state = dismissState,
        background = { SwipeBackBox(targetValue = dismissState.targetValue) },
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(0.2f) },
        modifier = modifier
            .padding(16.dp, 4.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            content()
        }
    }
}

@Composable
private fun SwipeBackBox(
    targetValue: DismissValue,
    modifier: Modifier = Modifier,
) {
    val color by animateColorAsState(
        when (targetValue) {
            DismissValue.Default -> Color.White
            else -> Color.Red
        }, label = StringEmpty
    )

    val scale by animateFloatAsState(
        if (targetValue == DismissValue.Default) 0.75f else 1f, label = StringEmpty
    )

    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = Dp(20f))
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            modifier = Modifier.scale(scale)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
    }
}

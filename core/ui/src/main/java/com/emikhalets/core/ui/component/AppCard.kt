package com.emikhalets.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.ui.BoxPreview
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.stroke
import com.emikhalets.core.ui.theme.textHeader

@Deprecated("Use AppCardColumn or AppCardRow")
@OptIn(ExperimentalFoundationApi::class)
@Composable
@NonRestartableComposable
fun AppCard(
    modifier: Modifier = Modifier,
    header: String = "",
    headerSize: TextUnit = 20.sp,
    hasBorder: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = shape,
        backgroundColor = backgroundColor,
        border = if (hasBorder) BorderStroke(1.dp, MaterialTheme.colors.stroke) else null,
        elevation = 0.dp,
        modifier = modifier
            .clip(shape)
            .combinedClickable(
                // TODO: block click block any types
//                enabled = onClick != null || onDoubleClick != null || onLongClick != null,
                onClick = { onClick?.invoke() },
                onDoubleClick = { onDoubleClick?.invoke() },
                onLongClick = { onLongClick?.invoke() },
            ),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (header.isNotBlank()) {
                Text(
                    text = header,
                    style = MaterialTheme.typography.textHeader,
                    maxLines = 1,
                    fontSize = headerSize,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                AppDivider()
            }
            content()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@NonRestartableComposable
fun AppCardBase(
    modifier: Modifier = Modifier,
    header: String = "",
    headerSize: TextUnit = 20.sp,
    hasBorder: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Card(
        shape = shape,
        backgroundColor = backgroundColor,
        border = if (hasBorder) BorderStroke(1.dp, MaterialTheme.colors.stroke) else null,
        elevation = 0.dp,
        modifier = modifier
            .clip(shape)
            .combinedClickable(
                // TODO: block click block any types
//                enabled = onClick != null || onDoubleClick != null || onLongClick != null,
                onClick = { onClick?.invoke() },
                onDoubleClick = { onDoubleClick?.invoke() },
                onLongClick = { onLongClick?.invoke() },
            ),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (header.isNotBlank()) {
                Text(
                    text = header,
                    style = MaterialTheme.typography.textHeader,
                    maxLines = 1,
                    fontSize = headerSize,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                AppDivider()
            }
            content()
        }
    }
}

@Composable
@NonRestartableComposable
fun AppCardColumn(
    modifier: Modifier = Modifier,
    header: String = "",
    headerSize: TextUnit = 20.sp,
    hasBorder: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    AppCardBase(
        modifier = modifier,
        header = header,
        headerSize = headerSize,
        hasBorder = hasBorder,
        shape = shape,
        backgroundColor = backgroundColor,
        onClick = onClick,
        onDoubleClick = onDoubleClick,
        onLongClick = onLongClick
    ) {
        Column(
            content = content,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        )
    }
}

@Composable
@NonRestartableComposable
fun AppCardRow(
    modifier: Modifier = Modifier,
    header: String = "",
    headerSize: TextUnit = 20.sp,
    hasBorder: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    AppCardBase(
        modifier = modifier,
        header = header,
        headerSize = headerSize,
        hasBorder = hasBorder,
        shape = shape,
        backgroundColor = backgroundColor,
        onClick = onClick,
        onDoubleClick = onDoubleClick,
        onLongClick = onLongClick
    ) {
        Row(
            content = content,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        )
    }
}

@BoxPreview
@Composable
private fun Preview() {
    AppTheme {
        Column {
            AppCardColumn(
                header = "Card header",
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Card with border", modifier = Modifier.padding(8.dp))
                Text(text = "Card with border", modifier = Modifier.padding(8.dp))
                Text(text = "Card with border", modifier = Modifier.padding(8.dp))
                Text(text = "Card with border", modifier = Modifier.padding(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            AppCardRow(
                header = "Card header",
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Card with border", modifier = Modifier.padding(8.dp))
                Text(text = "Card with border", modifier = Modifier.padding(8.dp))
                Text(text = "Card with border", modifier = Modifier.padding(8.dp))
                Text(text = "Card with border", modifier = Modifier.padding(8.dp))
            }
        }
    }
}

package com.emikhalets.superapp.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.core.ui.theme.body1
import com.emikhalets.superapp.core.ui.theme.body2
import com.emikhalets.superapp.core.ui.theme.button
import com.emikhalets.superapp.core.ui.theme.caption
import com.emikhalets.superapp.core.ui.theme.header
import com.emikhalets.superapp.core.ui.theme.textSecondary
import com.emikhalets.superapp.core.ui.theme.title

@Composable
fun AppTextHeader(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.header,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}

@Composable
fun AppTextTitle(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.title,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}

@Composable
fun AppTextPrimary(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.body1,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}

@Composable
fun AppTextSecondary(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.textSecondary,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.body2,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}

@Composable
fun ButtonBorderless(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.button,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}

@Composable
fun AppTextCaption(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.textSecondary,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.caption,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun TextPreview() {
    AppTheme {
        Column(Modifier.fillMaxWidth()) {
            AppTextHeader(
                text = "Text Header", modifier = Modifier.padding(8.dp, 0.dp)
            )
            AppTextTitle(
                text = "Text Title", modifier = Modifier.padding(8.dp, 0.dp)
            )
            AppTextPrimary(
                text = "Text Primary", modifier = Modifier.padding(8.dp, 0.dp)
            )
            AppTextSecondary(
                text = "Text Secondary", modifier = Modifier.padding(8.dp, 0.dp)
            )
            ButtonBorderless(
                text = "Text Button", modifier = Modifier.padding(8.dp, 0.dp)
            )
            AppTextCaption(
                text = "Text Caption", modifier = Modifier.padding(8.dp, 0.dp)
            )
        }
    }
}
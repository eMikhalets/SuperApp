package com.emikhalets.superapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.ApplicationEntity
import com.emikhalets.core.common.isEnabled
import com.emikhalets.core.common.isVisible
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.getIcon
import com.emikhalets.core.ui.getName
import com.emikhalets.core.ui.theme.AppTheme

private const val TAG = "Main"

@Composable
fun MainScreen(
    navigateToApp: (type: ApplicationEntity) -> Unit,
    navigateToWidget: (Int) -> Unit,
    navigateToNewWidget: () -> Unit,
) {
    logi(TAG, "Invoke")
    ScreenContent(
        onApplicationClick = navigateToApp,
        onWidgetClick = navigateToWidget,
        onAddWidgetClick = navigateToNewWidget,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ScreenContent(
    onApplicationClick: (ApplicationEntity) -> Unit,
    onWidgetClick: (Int) -> Unit,
    onAddWidgetClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        MainItemBox(stringResource(R.string.app_main_header_applications)) {
            FlowRow(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                ApplicationEntity.values().forEach { application ->
                    if (application.isVisible()) {
                        ApplicationButton(
                            application = application,
                            onClick = { onApplicationClick(application) }
                        )
                    }
                }
            }
        }
        MainItemBox(stringResource(R.string.app_main_header_widgets)) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(70.dp)
                    .background(
                        color = MaterialTheme.colors.secondary,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { onAddWidgetClick() }
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun MainItemBox(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    logi("$TAG.MainItemBox", "Invoke")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            MainHeaderText(
                text = label,
                modifier = Modifier.padding(8.dp)
            )
            Divider()
            content()
        }
    }
}

@Composable
private fun MainHeaderText(
    text: String,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.MainHeaderText", "Invoke: text = $text")
    Text(
        text = text,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun ApplicationButton(
    application: ApplicationEntity,
    onClick: (ApplicationEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.ApplicationButton", "Invoke: appType = ${application.getName()}")

    val backColor = if (application.isEnabled()) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.secondary
    }

    val textColor = if (application.isEnabled()) {
        MaterialTheme.colors.onSurface
    } else {
        MaterialTheme.colors.secondaryVariant
    }

    Column(modifier = modifier.padding(12.dp)) {
        Icon(
            imageVector = application.getIcon(),
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .size(80.dp)
                .background(color = backColor, shape = MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
                .clickable(application.isEnabled()) { onClick(application) }
                .padding(16.dp)
        )
        Text(
            text = application.getName(),
            color = textColor,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(top = 6.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            onApplicationClick = {},
            onWidgetClick = {},
            onAddWidgetClick = {},
        )
    }
}

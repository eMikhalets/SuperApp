package com.emikhalets.superapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.superapp.utils.AppType

@Composable
fun MainScreen(
    navigateToApp: (type: AppType) -> Unit,
    navigateToWidget: (Int) -> Unit,
    navigateToNewWidget: () -> Unit,
) {
    ScreenContent(
        onEventsAppClick = { navigateToApp(AppType.Events) },
        onFinancesAppClick = { navigateToApp(AppType.Finances) },
        onFitnessAppClick = { navigateToApp(AppType.Fitness) },
        onMediaLibAppClick = { navigateToApp(AppType.MediaLib) },
        onNotesAppClick = { navigateToApp(AppType.Notes) },
        onWidgetClick = navigateToWidget,
        onAddWidgetClick = navigateToNewWidget,
    )
}

@Composable
private fun ScreenContent(
    onEventsAppClick: () -> Unit,
    onFinancesAppClick: () -> Unit,
    onFitnessAppClick: () -> Unit,
    onMediaLibAppClick: () -> Unit,
    onNotesAppClick: () -> Unit,
    onWidgetClick: (Int) -> Unit,
    onAddWidgetClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Applications(
            onEventsAppClick = onEventsAppClick,
            onFinancesAppClick = onFinancesAppClick,
            onFitnessAppClick = onFitnessAppClick,
            onMediaLibAppClick = onMediaLibAppClick,
            onNotesAppClick = onNotesAppClick
        )
        MenuWidgets(
            onWidgetClick = onWidgetClick,
            onAddWidgetClick = onAddWidgetClick
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Applications(
    onEventsAppClick: () -> Unit,
    onFinancesAppClick: () -> Unit,
    onFitnessAppClick: () -> Unit,
    onMediaLibAppClick: () -> Unit,
    onNotesAppClick: () -> Unit,
) {
    MenuHeader(stringResource(R.string.app_applications))
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        MenuButton(
            appType = AppType.Events,
            onClick = onEventsAppClick
        )
        MenuButton(
            appType = AppType.Finances,
            onClick = onFinancesAppClick
        )
        MenuButton(
            appType = AppType.Fitness,
            onClick = onFitnessAppClick
        )
        MenuButton(
            appType = AppType.MediaLib,
            onClick = onMediaLibAppClick
        )
        MenuButton(
            appType = AppType.Notes,
            onClick = onNotesAppClick
        )
    }
}

@Composable
private fun MenuWidgets(
    onWidgetClick: (Int) -> Unit,
    onAddWidgetClick: () -> Unit,
) {
    MenuHeader(stringResource(R.string.app_menu_widgets))
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

@Composable
@NonRestartableComposable
private fun MenuHeader(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(16.dp, 8.dp)
    )
}

@Composable
private fun MenuButton(
    appType: AppType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(12.dp)) {
        Icon(
            imageVector = appType.appIcon,
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .clip(MaterialTheme.shapes.medium)
                .clickable { onClick() }
                .padding(16.dp)

        )
        Text(
            text = stringResource(appType.appNameRes),
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
    com.emikhalets.core.ui.theme.AppTheme {
        ScreenContent(
            onEventsAppClick = {},
            onFinancesAppClick = {},
            onFitnessAppClick = {},
            onMediaLibAppClick = {},
            onNotesAppClick = {},
            onWidgetClick = {},
            onAddWidgetClick = {},
        )
    }
}
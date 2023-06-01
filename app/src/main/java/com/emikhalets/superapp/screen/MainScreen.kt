package com.emikhalets.superapp.screen

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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.superapp.R
import com.emikhalets.superapp.utils.AppType
import com.emikhalets.ui.theme.AppTheme

@Composable
fun MainScreen(
    navigateToApp: (type: AppType) -> Unit,
) {
    ScreenContent(
        onEventsAppClick = { navigateToApp(AppType.Events) },
        onFinancesAppClick = { navigateToApp(AppType.Finances) },
        onFitnessAppClick = { navigateToApp(AppType.Fitness) },
        onMediaLibAppClick = { navigateToApp(AppType.MediaLib) },
        onNotesAppClick = { navigateToApp(AppType.Notes) },
    )
}

@Composable
private fun ScreenContent(
    onEventsAppClick: () -> Unit,
    onFinancesAppClick: () -> Unit,
    onFitnessAppClick: () -> Unit,
    onMediaLibAppClick: () -> Unit,
    onNotesAppClick: () -> Unit,
) {
    Applications(
        onEventsAppClick = onEventsAppClick,
        onFinancesAppClick = onFinancesAppClick,
        onFitnessAppClick = onFitnessAppClick,
        onMediaLibAppClick = onMediaLibAppClick,
        onNotesAppClick = onNotesAppClick
    )
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
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.app_applications),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(16.dp, 8.dp)
        )
        FlowRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
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
}

@Composable
private fun MenuButton(
    appType: AppType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(12.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = appType.appIcon,
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .size(90.dp)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp)

        )
        Text(
            text = appType.appName,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
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
            onEventsAppClick = {},
            onFinancesAppClick = {},
            onFitnessAppClick = {},
            onMediaLibAppClick = {},
            onNotesAppClick = {},
        )
    }
}

package com.emikhalets.superapp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.emikhalets.core.common.ApplicationItem
import com.emikhalets.core.ui.extentions.ScreenPreview
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun MainScreen(
    navigateToApplication: (application: ApplicationItem) -> Unit,
) {
    val applications = remember { ApplicationItem.getAll() }
    ScreenContent(
        applications = applications,
        onApplicationClick = navigateToApplication,
    )
}

@Composable
private fun ScreenContent(
    applications: List<ApplicationItem>,
    onApplicationClick: (ApplicationItem) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(applications) { application ->
            ApplicationButton(
                application = application,
                onClick = { onApplicationClick(application) },
            )
        }
    }
}

@Composable
private fun ApplicationButton(
    application: ApplicationItem,
    onClick: (ApplicationItem) -> Unit,
) {
    IconButton(onClick = { onClick(application) }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(application.icon),
                contentDescription = null
            )
            Text(
                text = stringResource(application.name),
            )
        }
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            applications = ApplicationItem.getAll(),
            onApplicationClick = {},
        )
    }
}

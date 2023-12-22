package com.emikhalets.superapp

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Button(
        onClick = { onClick(application) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)

    ) {
        Icon(
            painter = painterResource(application.icon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(application.name),
            fontSize = 18.sp
        )
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

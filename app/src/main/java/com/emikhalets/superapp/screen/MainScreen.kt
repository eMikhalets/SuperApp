package com.emikhalets.superapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.ui.component.AppHeaderCardColumn
import com.emikhalets.core.ui.extentions.ScreenPreview
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.superapp.ApplicationType
import com.emikhalets.superapp.R
import com.emikhalets.superapp.getIcon
import com.emikhalets.superapp.getName

@Composable
fun MainScreen(
    navigateToApplication: (application: ApplicationType) -> Unit,
) {
    val applications = remember { ApplicationType.getList() }
    ScreenContent(
        applications = applications,
        onApplicationClick = navigateToApplication,
    )
}

@Composable
private fun ScreenContent(
    applications: List<ApplicationType>,
    onApplicationClick: (ApplicationType) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppHeaderCardColumn(
            header = stringResource(R.string.app_widgets),
            content = { WidgetsBox() },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .padding(horizontal = 8.dp)
                .weight(1f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppHeaderCardColumn(
            header = stringResource(R.string.app_applications),
            content = { ApplicationsBox(applications, onApplicationClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
private fun WidgetsBox(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.app_widgets_not_implemented),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun ApplicationsBox(
    applications: List<ApplicationType>,
    onApplicationClick: (ApplicationType) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth()
    ) {
        items(applications) { application ->
            ApplicationButton(
                application = application,
                onClick = { onApplicationClick(application) },
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ApplicationButton(
    application: ApplicationType,
    onClick: (ApplicationType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .padding(6.dp)
            .background(color = MaterialTheme.colors.primary)
            .clickable { onClick(application) }
    ) {
        Icon(
            imageVector = application.getIcon(),
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = application.getName(),
            color = MaterialTheme.colors.onPrimary,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            applications = ApplicationType.getList(),
            onApplicationClick = {},
        )
    }
}

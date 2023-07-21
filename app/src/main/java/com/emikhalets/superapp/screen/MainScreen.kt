package com.emikhalets.superapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.common.classNames
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.ApplicationEntity
import com.emikhalets.core.ui.ScreenPreview
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.getIcon
import com.emikhalets.core.ui.getName
import com.emikhalets.core.ui.isEnabled
import com.emikhalets.core.ui.isVisible
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.text
import com.emikhalets.core.ui.theme.textHeader
import com.emikhalets.superapp.R

private const val TAG = "Main"

@Composable
fun MainScreen(
    navigateToApp: (type: ApplicationEntity) -> Unit,
    navigateToWidget: (Int) -> Unit,
    navigateToNewWidget: () -> Unit,
) {
    logi(TAG, "Invoke")

    val applications = remember { ApplicationEntity.values() }

    ScreenContent(
        applications = applications,
        onApplicationClick = navigateToApp,
        onWidgetClick = navigateToWidget,
        onAddWidgetClick = navigateToNewWidget
    )
}

@Composable
private fun ScreenContent(
    applications: List<ApplicationEntity>,
    onApplicationClick: (ApplicationEntity) -> Unit,
    onWidgetClick: (Int) -> Unit,
    onAddWidgetClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke: applications = ${applications.classNames}")

    Column(modifier = Modifier.fillMaxSize()) {
        WidgetsBox(
            onWidgetClick = onWidgetClick,
            onAddWidgetClick = onAddWidgetClick,
            modifier = Modifier.weight(1f)
        )
        ApplicationsBox(
            applications = applications,
            onApplicationClick = onApplicationClick
        )
    }
}

//TODO: implement widgets draggable box
@Composable
private fun WidgetsBox(
    onWidgetClick: (Int) -> Unit,
    onAddWidgetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.WidgetsBox", "Invoke")

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.app_main_header_widgets),
            style = MaterialTheme.typography.textHeader,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Box(modifier = Modifier.fillMaxSize()) {
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
private fun ApplicationsBox(
    applications: List<ApplicationEntity>,
    onApplicationClick: (ApplicationEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.ApplicationsBox", "Invoke: applications = ${applications.classNames}")

    AppCard(
        header = stringResource(R.string.app_main_header_applications),
        shape = RectangleShape,
        modifier = modifier.fillMaxWidth()
    ) {
        ApplicationsFlowRow(
            applications = applications,
            onApplicationClick = onApplicationClick
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ApplicationsFlowRow(
    applications: List<ApplicationEntity>,
    onApplicationClick: (ApplicationEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.ApplicationsFlowRow", "Invoke: applications = ${applications.classNames}")

    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        applications.forEach { application ->
            if (application.isVisible()) {
                ApplicationButton(
                    application = application,
                    onClick = { onApplicationClick(application) }
                )
            }
        }
    }
}

@Composable
private fun ApplicationButton(
    application: ApplicationEntity,
    onClick: (ApplicationEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.ApplicationButton", "Invoke: appType = ${application.getName()}")

    val backColor = if (application.isEnabled()) MaterialTheme.colors.primary
    else Color.Transparent

    val contentColor = if (application.isEnabled()) MaterialTheme.colors.onPrimary
    else MaterialTheme.colors.secondary

    val strokeThickness = if (application.isEnabled()) 0.dp else 2.dp

    val shape = MaterialTheme.shapes.small

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .size(80.dp)
            .padding(4.dp)
            .clip(shape)
            .border(strokeThickness, MaterialTheme.colors.secondary, shape)
            .background(color = backColor, shape = shape)
            .clickable(application.isEnabled()) { onClick(application) }
    ) {
        Icon(
            imageVector = application.getIcon(),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = application.getName(),
            style = MaterialTheme.typography.text,
            color = contentColor,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(4.dp, 8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            applications = ApplicationEntity.values(),
            onApplicationClick = {},
            onWidgetClick = {},
            onAddWidgetClick = {}
        )
    }
}

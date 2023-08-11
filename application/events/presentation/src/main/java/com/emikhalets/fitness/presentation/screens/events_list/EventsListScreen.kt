package com.emikhalets.fitness.presentation.screens.events_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.presentation.R
import com.emikhalets.fitness.presentation.screens.events_list.EventsListContract.Action
import com.emikhalets.fitness.presentation.screens.events_list.EventsListContract.Effect
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.utils.extensions.formatHomeInfo
import com.emikhalets.simpleevents.utils.extensions.pluralsResource
import com.emikhalets.ui.component.AppText
import com.emikhalets.ui.component.AppTextField
import com.emikhalets.ui.component.ChildScreenBox
import com.emikhalets.ui.theme.AppTheme

@Composable
fun EventsListScreen(
    navigateToEvent: (id: Long) -> Unit,
    navigateBack: () -> Unit,
    viewModel: EventsListViewModel,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(null)

    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetEvents)
    }

    LaunchedEffect(effect) {
        when (val effectValue = effect) {
            is Effect.ErrorDialog -> errorMessage = effectValue.message?.asString(context) ?: ""
            null -> errorMessage = ""
        }
    }

    ScreenContent(
        searchQuery = state.searchQuery,
        events = state.events,
        onSearchQueryChange = { viewModel.setAction(Action.SearchEvents(it)) },
        onEventClick = navigateToEvent,
        onBackClick = navigateBack
    )

    if (errorMessage.isNotEmpty()) {
        ErrorDialog(
            message = errorMessage,
            onOkClick = { errorMessage = "" }
        )
    }
}

@Composable
private fun ScreenContent(
    searchQuery: String,
    events: List<EventEntity>,
    onSearchQueryChange: (String) -> Unit,
    onEventClick: (Long) -> Unit,
    onBackClick: () -> Unit,
) {
    ChildScreenBox(onBackClick, stringResource(com.emikhalets.common.R.string.app_events)) {
        AppTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = stringResource(R.string.events_list_search_placeholder),
            leadingIcon = Icons.Rounded.Search,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(events) { event ->
                EventListItem(
                    event = event,
                    onEventClick = onEventClick
                )
            }
        }
    }
}

@Composable
private fun EventListItem(
    event: EventEntity,
    onEventClick: (Long) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onEventClick(event.id) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxHeight()
                .aspectRatio(1f)
        ) {
            AppText(
                text = event.days.toString(),
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            AppText(
                text = pluralsResource(R.plurals.events_list_event_days, event.days),
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp)
        ) {
            AppText(
                text = event.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            AppText(
                text = event.formatHomeInfo(),
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            searchQuery = "Some query",
            events = previewEventsListScreen(),
            onSearchQueryChange = {},
            onEventClick = {},
            onBackClick = {}
        )
    }
}

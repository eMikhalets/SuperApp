package com.emikhalets.fitness.presentation.screens.events_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.common.R
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.fitness.presentation.screens.events_list.EventsListContract.Action
import com.emikhalets.fitness.presentation.screens.events_list.EventsListContract.Effect
import com.emikhalets.simpleevents.presentation.components.AppIcon
import com.emikhalets.simpleevents.presentation.components.AppText
import com.emikhalets.simpleevents.presentation.components.AppTextField
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.utils.extensions.formatDate
import com.emikhalets.simpleevents.utils.extensions.formatHomeInfo
import com.emikhalets.simpleevents.utils.extensions.pluralsResource
import com.emikhalets.ui.component.CHILD_SCREEN_BOX_PADDING
import com.emikhalets.ui.component.ChildScreenBox
import com.emikhalets.ui.theme.AppTheme

@Composable
fun EventsListScreen(
    navigateToNewEvent: () -> Unit,
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
        onNewEventClick = navigateToNewEvent,
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
    onNewEventClick: () -> Unit,
    onEventClick: (Long) -> Unit,
    onBackClick: () -> Unit,
) {
    ChildScreenBox(onBackClick, stringResource(R.string.app_events)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = CHILD_SCREEN_BOX_PADDING)
        ) {
            SearchBox(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
            )
            EventsListBox(
                eventsMap = state.eventsMap,
                onEventClick = onEventClick
            )
        }
    }
}

@Composable
private fun SearchBox(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    AppTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = stringResource(R.string.home_search_placeholder),
        leadingIcon = R.drawable.ic_round_search_24,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EventsListBox(
    eventsMap: Map<Long, List<EventEntity>>,
    onEventClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (eventsMap.isNotEmpty()) {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            eventsMap.forEach { (date, events) ->
                stickyHeader {
                    EventsListHeader(
                        date = date,
                        onHeaderClick = {}
                    )
                }
                items(events) { event ->
                    EventListItem(
                        event = event,
                        onEventClick = onEventClick
                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AppText(
                text = stringResource(R.string.home_empty_events),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()

            )
        }
    }
}

@Composable
private fun EventsListHeader(
    date: Long,
    onHeaderClick: (Long) -> Unit,
) {
    AppText(
        text = date.formatDate("MMMM yyyy"),
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .background(Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable { onHeaderClick(date) }
    )
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
        SquareColumn(backgroundColor = MaterialTheme.colors.primary) {
            AppText(
                text = event.days.toString(),
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            AppText(
                text = pluralsResource(R.plurals.home_event_days, event.days),
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )
        }
        SquareColumn(backgroundColor = MaterialTheme.colors.background) {
            AppIcon(
                drawableRes = R.drawable.ic_round_person_24,
                tint = MaterialTheme.colors.onSurface
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

@Composable
private fun SquareColumn(
    backgroundColor: Color,
    content: @Composable () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = { content() },
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxHeight()
            .aspectRatio(1f)
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            searchQuery = "Some query",
            events = previewEventsListScreen(),
            onSearchQueryChange = {},
            onNewEventClick = {},
            onEventClick = {},
            onBackClick = {}
        )
    }
}

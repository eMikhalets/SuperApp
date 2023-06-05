package com.emikhalets.medialib.presentation.screens.movies.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus.Companion.getIconRes
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.IconPrimary
import com.emikhalets.medialib.presentation.core.MediaLibAsyncImage
import com.emikhalets.medialib.presentation.core.MediaLibFullScreenText
import com.emikhalets.medialib.presentation.core.MediaLibSavedSearchBox
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.core.TextPrimary
import com.emikhalets.medialib.presentation.core.TextSecondary
import com.emikhalets.medialib.presentation.core.TextTitle
import com.emikhalets.medialib.presentation.theme.MediaLibTheme
import com.emikhalets.medialib.utils.toast

@Composable
fun MoviesScreen(
    navigateToMovieDetails: (id: Long) -> Unit,
    navigateToMovieEdit: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getMoviesList()
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            val message = error.asString(context)
            message.toast(context)
            viewModel.resetError()
        }
    }

    ScreenContent(
        state = state,
        searchQuery = query,
        onSearchQueryChange = {
            query = it
            viewModel.searchMovies(query)
        },
        onAddItemClick = navigateToMovieEdit,
        onItemClick = navigateToMovieDetails,
    )
}

@Composable
private fun ScreenContent(
    state: MoviesState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddItemClick: () -> Unit,
    onItemClick: (Long) -> Unit,
) {
    if (state.loading) {
        AppLoader()
    } else {
        MovieListBox(
            moviesList = state.movies,
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onAddItemClick = onAddItemClick,
            onItemClick = onItemClick,
        )
    }
}

@Composable
private fun MovieListBox(
    moviesList: List<MovieFullEntity>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddItemClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        MediaLibSavedSearchBox(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Box(modifier = Modifier.weight(1f)) {
            if (moviesList.isEmpty()) {
                MediaLibFullScreenText(text = stringResource(id = R.string.movies_empty_list))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    items(moviesList) { item ->
                        MovieBox(
                            entity = item,
                            onItemClick = onItemClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }
                }
            }
            FloatingActionButton(
                onClick = onAddItemClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun MovieBox(
    entity: MovieFullEntity,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onItemClick(entity.movieEntity.id) }
    ) {
        MediaLibAsyncImage(
            url = entity.movieEntity.poster,
            height = 100.dp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextTitle(
                    text = entity.movieEntity.title,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                val statusIcon = entity.movieEntity.watchStatus.getIconRes()
                if (statusIcon != null) {
                    IconPrimary(drawableRes = statusIcon)
                }
            }
            if (entity.movieEntity.titleRu.isNotEmpty()) {
                TextSecondary(
                    text = entity.movieEntity.titleRu,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (entity.movieEntity.rating > 0) {
                RatingBar(
                    rating = entity.movieEntity.rating,
                    pointSize = 14.dp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            val info = entity.formatListItemInfo()
            if (info.isNotEmpty()) {
                TextPrimary(
                    text = info,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    MediaLibTheme {
        ScreenContent(
            state = MoviesState(
                movies = listOf(MovieFullEntity(
                    movieEntity = MovieEntity(
                        id = 0,
                        title = "Movie name",
                        titleRu = "Movie name",
                        year = 2015,
                        rating = 4,
                        watchStatus = MovieWatchStatus.WATCH,
                        overview = "",
                        poster = "",
                        saveTimestamp = 0,
                        lastUpdateTimestamp = 0,
                        comment = "",
                        runtime = "",
                        awards = ""
                    ),
                    genres = listOf(
                        GenreEntity("Action", GenreType.MOVIE),
                        GenreEntity("Drama", GenreType.MOVIE),
                        GenreEntity("Action", GenreType.MOVIE),
                        GenreEntity("Drama", GenreType.MOVIE),
                        GenreEntity("Action", GenreType.MOVIE),
                        GenreEntity("Drama", GenreType.MOVIE)
                    ),
                    ratings = emptyList(),
                    crew = emptyList()
                ))
            ),
            searchQuery = "",
            onSearchQueryChange = {},
            onAddItemClick = {},
            onItemClick = {}
        )
    }
}
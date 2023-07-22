package com.emikhalets.medialib.presentation.screens.movies.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.crew.CrewEntity
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import com.emikhalets.medialib.domain.entities.ratings.CrewType
import com.emikhalets.medialib.domain.entities.ratings.RatingEntity
import com.emikhalets.medialib.domain.entities.utils.MenuIconEntity
import com.emikhalets.medialib.presentation.core.MediaLibAsyncImage
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.AppTextFullScreen
import com.emikhalets.medialib.presentation.core.AppTopBar
import com.emikhalets.medialib.presentation.core.DetailsSection
import com.emikhalets.medialib.presentation.core.DetailsSectionList
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.core.TextPrimary
import com.emikhalets.medialib.presentation.core.TextSecondary
import com.emikhalets.medialib.presentation.core.TextTitle
import com.emikhalets.medialib.presentation.dialogs.DeleteFromDbDialog
import com.emikhalets.medialib.presentation.dialogs.PosterEditDialog
import com.emikhalets.medialib.presentation.theme.MediaLibTheme
import com.emikhalets.medialib.utils.getRandomText
import com.emikhalets.medialib.utils.toast

@Composable
fun MovieDetailsScreen(
    navigateToMovieEdit: (movieId: Long) -> Unit,
    navigateBack: () -> Unit,
    movieId: Long,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var poster by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showPosterDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getMovie(movieId)
    }

    LaunchedEffect(state.entity) {
        val entity = state.entity
        if (entity != null) {
            poster = entity.movieEntity.poster
            rating = entity.movieEntity.rating
        }
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            val message = error.asString(context)
            message.toast(context)
            viewModel.resetError()
        }
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) {
            navigateBack()
        }
    }

    if (state.loading) {
        AppLoader()
    } else {
        val entity = state.entity
        if (entity == null) {
            AppTextFullScreen()
        } else {
            DetailsScreen(
                entity = entity,
                poster = poster,
                rating = rating,
                onRatingChange = {
                    rating = it
                    viewModel.updateMovieRating(rating)
                },
                onPosterClick = { showPosterDialog = true },
                onMovieEditClick = navigateToMovieEdit,
                onBackClick = navigateBack,
                onDeleteClick = { showDeleteDialog = true }
            )
        }
    }

    if (showDeleteDialog) {
        DeleteFromDbDialog(
            onDismiss = { showDeleteDialog = false },
            onDeleteClick = {
                showDeleteDialog = false
                viewModel.deleteMovie()
            }
        )
    }

    if (showPosterDialog) {
        PosterEditDialog(
            url = state.entity?.movieEntity?.poster ?: "",
            onDismiss = { showPosterDialog = false },
            onSaveClick = { url ->
                showPosterDialog = false
                poster = url
                viewModel.updateMoviePoster(poster)
            }
        )
    }
}

@Composable
private fun DetailsScreen(
    entity: MovieFullEntity,
    poster: String,
    rating: Int,
    onRatingChange: (Int) -> Unit,
    onPosterClick: () -> Unit,
    onMovieEditClick: (movieId: Long) -> Unit,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(id = R.string.screen_title_movie_details),
            onNavigateBack = onBackClick,
            actions = listOf(
                MenuIconEntity(R.drawable.ic_round_edit_24) {
                    onMovieEditClick(entity.movieEntity.id)
                },
                MenuIconEntity(R.drawable.ic_round_delete_24) {
                    onDeleteClick()
                }
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(IntrinsicSize.Min)
            ) {
                MediaLibAsyncImage(
                    url = poster,
                    height = 170.dp,
                    onClick = { onPosterClick() }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(8.dp)
                        .padding(start = 8.dp)
                ) {
                    TextTitle(
                        text = entity.movieEntity.title,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (entity.movieEntity.titleRu.isNotEmpty()) {
                        TextSecondary(
                            text = entity.movieEntity.titleRu,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    val info = entity.formatDetailsInfo()
                    if (info.isNotEmpty()) {
                        TextPrimary(
                            text = info,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                    RatingBar(
                        rating = rating,
                        onRatingChange = onRatingChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
            DetailsSection(
                header = stringResource(R.string.movie_details_genres),
                content = entity.formatGenres(),
                modifier = Modifier.padding(top = 8.dp)
            )
            DetailsSectionList(
                header = stringResource(R.string.movie_details_ratings),
                content = entity.formatRatings(),
                modifier = Modifier.padding(top = 8.dp)
            )
            DetailsSectionList(
                header = stringResource(R.string.movie_details_crew),
                content = entity.formatCrew(),
                modifier = Modifier.padding(top = 8.dp)
            )
            DetailsSection(
                header = stringResource(R.string.movie_details_awards),
                content = entity.movieEntity.awards,
                modifier = Modifier.padding(top = 8.dp)
            )
            DetailsSection(
                header = stringResource(R.string.movie_details_overview),
                content = entity.movieEntity.overview,
                modifier = Modifier.padding(top = 8.dp)
            )
            DetailsSection(
                header = stringResource(R.string.movie_details_comment),
                content = entity.movieEntity.comment,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    MediaLibTheme {
        DetailsScreen(
            entity = MovieFullEntity(
                movieEntity = MovieEntity(
                    id = 0,
                    title = "Movie name",
                    titleRu = "Movie name rus",
                    year = 2015,
                    rating = 4,
                    watchStatus = MovieWatchStatus.WATCH,
                    overview = getRandomText(20),
                    poster = "",
                    saveTimestamp = 0,
                    lastUpdateTimestamp = 0,
                    comment = getRandomText(20),
                    runtime = "123 min",
                    awards = getRandomText(5)
                ),
                genres = listOf(
                    GenreEntity("Action", GenreType.MOVIE),
                    GenreEntity("DramaDrama", GenreType.MOVIE),
                    GenreEntity("Action", GenreType.MOVIE),
                    GenreEntity("DramaDrama", GenreType.MOVIE),
                    GenreEntity("ActionAction", GenreType.MOVIE),
                    GenreEntity("Drama", GenreType.MOVIE)
                ),
                ratings = listOf(
                    RatingEntity("IMDB", "123"),
                    RatingEntity("IMDB", "123"),
                    RatingEntity("IMDB", "123")
                ),
                crew = listOf(
                    CrewEntity("Name name name", CrewType.DIRECTOR),
                    CrewEntity("Name name name", CrewType.WRITER),
                    CrewEntity("Name name name", CrewType.ACTOR)
                )
            ),
            poster = "",
            rating = 4,
            onRatingChange = {},
            onPosterClick = {},
            onMovieEditClick = {},
            onBackClick = {},
            onDeleteClick = {}
        )
    }
}
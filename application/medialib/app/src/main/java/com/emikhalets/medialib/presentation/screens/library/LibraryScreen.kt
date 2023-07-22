package com.emikhalets.medialib.presentation.screens.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.core.MediaLibMainMenuButton
import com.emikhalets.medialib.presentation.core.MediaLibMainMenuHeader
import com.emikhalets.medialib.presentation.theme.MediaLibTheme

@Composable
fun LibraryScreen(
    navigateToMovies: () -> Unit,
    navigateToSerials: () -> Unit,
) {
    ScreenContent(
        onMoviesClick = navigateToMovies,
        onSerialsClick = navigateToSerials,
    )
}

@Composable
private fun ScreenContent(
    onMoviesClick: () -> Unit,
    onSerialsClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        MediaLibMainMenuHeader(text = stringResource(id = R.string.library))
        Row(modifier = Modifier.fillMaxWidth()) {
            MediaLibMainMenuButton(
                text = stringResource(id = R.string.movies),
                iconRes = R.drawable.ic_round_local_movies_24,
                onClick = onMoviesClick
            )
            MediaLibMainMenuButton(
                text = stringResource(id = R.string.serials),
                iconRes = R.drawable.ic_round_tv_24,
                onClick = onSerialsClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    MediaLibTheme {
        ScreenContent(
            onMoviesClick = {},
            onSerialsClick = {}
        )
    }
}
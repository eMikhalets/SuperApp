package com.emikhalets.medialib.presentation.navigation

import com.emikhalets.medialib.R

enum class MediaLibScreen(val route: String) {

    LIBRARY("library"),
    SEARCH("search"),
    SAVED_MOVIES("saved_movies"),
    MovieDetails("movie_details"),
    MovieEdit("movie_edit"),
    Serials("serials"),
    SerialDetails("serial_details"),
    SerialEdit("serial_edit"),
    Searching("searching"),
    SearchImdb("search_imdb");

    companion object {

        fun getBottomBarItems(): List<MediaLibBottomBarItem> {
            return listOf(
                MediaLibBottomBarItem(
                    screen = LIBRARY,
                    titleRes = R.string.library,
                    iconRes = R.drawable.ic_round_home_24
                ),
                MediaLibBottomBarItem(
                    screen = SEARCH,
                    titleRes = R.string.search,
                    iconRes = R.drawable.ic_round_search_24
                )
            )
        }
    }
}
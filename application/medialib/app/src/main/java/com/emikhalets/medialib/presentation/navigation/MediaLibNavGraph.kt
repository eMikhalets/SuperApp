package com.emikhalets.medialib.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.medialib.presentation.screens.library.LibraryScreen
import com.emikhalets.medialib.presentation.screens.movies.details.MovieDetailsScreen
import com.emikhalets.medialib.presentation.screens.movies.edit.MovieEditScreen
import com.emikhalets.medialib.presentation.screens.movies.list.MoviesScreen
import com.emikhalets.medialib.presentation.screens.search.SearchScreen
import com.emikhalets.medialib.presentation.screens.searching.SearchMainScreen
import com.emikhalets.medialib.presentation.screens.searching.SearchWithImdbScreen
import com.emikhalets.medialib.presentation.screens.serials.details.SerialDetailsScreen
import com.emikhalets.medialib.presentation.screens.serials.edit.SerialEditScreen
import com.emikhalets.medialib.presentation.screens.serials.list.SerialsScreen

@Composable
fun MediaLibNavGraph(navController: NavHostController) {
    NavHost(navController, MediaLibScreen.LIBRARY.route) {

        composable(MediaLibScreen.LIBRARY.route) {
            LibraryScreen(
                navigateToMovies = { navController.navigate(MediaLibScreen.SAVED_MOVIES.route) },
                navigateToSerials = { navController.navigate(MediaLibScreen.Serials.route) }
            )
        }

        composable(MediaLibScreen.SEARCH.route) {
            SearchScreen(
                navigateToMovies = { navController.navigate(MediaLibScreen.SAVED_MOVIES.route) },
                navigateToSerials = { navController.navigate(MediaLibScreen.Serials.route) }
            )
        }

        composable(MediaLibScreen.SAVED_MOVIES.route) {
            MoviesScreen(
                navigateToMovieDetails = { id ->
                    navController.navigate("${MediaLibScreen.MovieDetails.route}/$id")
                },
                navigateToMovieEdit = {
                    navController.navigate("${MediaLibScreen.MovieEdit.route}/0")
                },
            )
        }

        composable(
            route = "${MediaLibScreen.MovieDetails.route}/{${NavArgs.MOVIE_ID}}",
            arguments = listOf(navArgument(NavArgs.MOVIE_ID) { type = NavType.LongType })
        ) {
            MovieDetailsScreen(
                navigateToMovieEdit = { movieId ->
                    navController.navigate("${MediaLibScreen.MovieEdit.route}/$movieId")
                },
                navigateBack = { navController.popBackStack() },
                movieId = it.arguments?.getLong(NavArgs.MOVIE_ID) ?: 0,
            )
        }

        composable(
            route = "${MediaLibScreen.MovieEdit.route}/{${NavArgs.MOVIE_ID}}",
            arguments = listOf(navArgument(NavArgs.MOVIE_ID) { type = NavType.LongType })
        ) {
            MovieEditScreen(
                navigateBack = { navController.popBackStack() },
                movieId = it.arguments?.getLong(NavArgs.MOVIE_ID) ?: 0,
            )
        }

        composable(MediaLibScreen.Serials.route) {
            SerialsScreen(
                navigateToSerialDetails = { serialId ->
                    navController.navigate("${MediaLibScreen.SerialDetails.route}/$serialId")
                },
                navigateToSerialEdit = {
                    navController.navigate("${MediaLibScreen.SerialEdit.route}/0")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${MediaLibScreen.SerialDetails.route}/{${NavArgs.SERIAL_ID}}",
            arguments = listOf(navArgument(NavArgs.SERIAL_ID) { type = NavType.LongType })
        ) {
            SerialDetailsScreen(
                navigateToSerialEdit = { movieId ->
                    navController.navigate("${MediaLibScreen.SerialEdit.route}/$movieId")
                },
                navigateBack = { navController.popBackStack() },
                serialId = it.arguments?.getLong(NavArgs.SERIAL_ID) ?: 0,
            )
        }

        composable(
            route = "${MediaLibScreen.SerialEdit.route}/{${NavArgs.SERIAL_ID}}",
            arguments = listOf(navArgument(NavArgs.SERIAL_ID) { type = NavType.LongType })
        ) {
            SerialEditScreen(
                navigateBack = { navController.popBackStack() },
                serialId = it.arguments?.getLong(NavArgs.SERIAL_ID) ?: 0,
            )
        }

        composable(MediaLibScreen.Searching.route) {
            SearchMainScreen(
                navigateImdbSearching = { navController.navigate(MediaLibScreen.SearchImdb.route) }
            )
        }

        composable(MediaLibScreen.SearchImdb.route) {
            SearchWithImdbScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

    }
}
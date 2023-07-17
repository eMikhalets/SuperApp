package com.emikhalets.medialib.presentation.screens.movies.list

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.use_case.movies.GetMoviesFlowUseCase
import com.emikhalets.medialib.utils.BaseViewModel
import com.emikhalets.medialib.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesFlowUseCase: GetMoviesFlowUseCase,
) : BaseViewModel<MoviesState>() {

    private var searchJob: Job? = null
    private var moviesList: List<MovieFullEntity> = emptyList()

    override fun initState() = MoviesState()

    fun resetError() = setState { it.copy(error = null) }

    fun getMoviesList() {
        launchIO {
            setState { it.copy(loading = true) }
            getMoviesFlowUseCase()
                .onSuccess { handleMoviesListSuccess(it) }
                .onFailure { handleFailure(it) }
        }
    }

    fun searchMovies(query: String) {
        cancelJob(searchJob, "Starting a new search request")
        searchJob = launchIO {
            delay(750)
            if (query.isEmpty()) {
                setState { it.copy(movies = moviesList) }
            } else {
                val newMoviesList = moviesList.filter {
                    it.movieEntity.title.contains(query) || it.movieEntity.titleRu.contains(query)
                }
                setState { it.copy(movies = newMoviesList) }
            }
        }
    }

    private suspend fun handleMoviesListSuccess(flow: Flow<List<MovieFullEntity>>) {
        flow.collectLatest { list ->
            moviesList = list
            setState { it.copy(loading = false, movies = list) }
        }
    }

    private fun handleFailure(throwable: Throwable) {
        setState { it.copy(loading = false, error = UiString.create(throwable.message)) }
    }
}
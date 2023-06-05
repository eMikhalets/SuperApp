package com.emikhalets.medialib.domain.use_case.movies

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesFlowUseCase(
    private val databaseRepository: DatabaseRepository,
) {

    suspend operator fun invoke(): Result<Flow<List<MovieFullEntity>>> {
        return databaseRepository.getMoviesListFlowOrderByLastUpdated()
    }
}
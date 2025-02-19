package eu.wojtach.tmdbclient.domain.usecase

import android.util.Log
import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.model.Movie
import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import eu.wojtach.tmdbclient.domain.repository.MovieRepository
import eu.wojtach.tmdbclient.domain.result.Result
import org.koin.core.annotation.Factory

@Factory
class GetMoviesUseCase(
    private val filterRepository: FilterRepository,
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(page: Int): Result<List<Movie>, DataError> {
        val selectedFilterId = filterRepository.getSelectedId()
        val movies = movieRepository.getAll(page, selectedFilterId)
        return movies
    }
}

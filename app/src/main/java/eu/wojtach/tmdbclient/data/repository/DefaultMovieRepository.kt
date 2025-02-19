package eu.wojtach.tmdbclient.data.repository

import eu.wojtach.tmdbclient.data.remote.DataSource
import eu.wojtach.tmdbclient.domain.model.Movie
import eu.wojtach.tmdbclient.domain.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.koin.core.annotation.Single

@Single
class DefaultMovieRepository(
    private val dataSource: DataSource
) : MovieRepository {
    override suspend fun getMovies(page: Int): List<Movie> = coroutineScope {
        val discoverResponse = dataSource.discovery(page)
        discoverResponse.results.map { movieResult ->
            async {
                val details = dataSource.details(movieResult.id).toDomain()
                movieResult.toDomain().copy(details = details)
            }
        }.awaitAll()
    }
}

package eu.wojtach.tmdbclient.data.repository

import eu.wojtach.tmdbclient.data.remote.movie.DataSource
import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.model.Movie
import eu.wojtach.tmdbclient.domain.repository.MovieRepository
import eu.wojtach.tmdbclient.domain.result.Result
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.koin.core.annotation.Single
import java.net.SocketTimeoutException

@Single
class DefaultMovieRepository(
    private val dataSource: DataSource
) : MovieRepository {
    override suspend fun getAll(page: Int, genreId: Long?): Result<List<Movie>, DataError> =
        coroutineScope {
            try {
                val discoverResponse = dataSource.discovery(page, genreId)
                val movies = discoverResponse.results.map { movieResult ->
                    async {
                        val details = dataSource.details(movieResult.id).toDomain()
                        movieResult.toDomain().copy(details = details)
                    }
                }.awaitAll()
                Result.Success(movies)
            } catch (e: SocketTimeoutException) {
                Result.Error(DataError.Timeout)
            } catch (e: HttpRequestTimeoutException) {
                Result.Error(DataError.Timeout)
            } catch (e: Exception) {
                Result.Error(DataError.Unknown)
            }
        }
}

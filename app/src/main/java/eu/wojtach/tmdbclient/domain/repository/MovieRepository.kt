package eu.wojtach.tmdbclient.domain.repository

import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.model.Movie
import eu.wojtach.tmdbclient.domain.result.Result

interface MovieRepository {

    suspend fun getAll(page: Int, genreId: Long?): Result<List<Movie>, DataError>
}

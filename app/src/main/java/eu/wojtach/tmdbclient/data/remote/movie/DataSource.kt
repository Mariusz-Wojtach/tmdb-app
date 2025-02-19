package eu.wojtach.tmdbclient.data.remote.movie

import eu.wojtach.tmdbclient.data.remote.movie.model.Detail
import eu.wojtach.tmdbclient.data.remote.movie.model.Page
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Factory

@Factory
class DataSource(
    private val ktorClient: HttpClient
) {

    suspend fun discovery(page: Int, genreId: Long?): Page {
        val result = ktorClient.get("/3/discover/movie") {
            parameter("page", page)
            genreId?.let { parameter("with_genres", it) }
        }
        return result.body()
    }

    suspend fun details(movieId: Long): Detail {
        val result = ktorClient.get("/3/movie/$movieId")
        return result.body()
    }
}

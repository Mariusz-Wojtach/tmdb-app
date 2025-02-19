package eu.wojtach.tmdbclient.data.remote.movie

import eu.wojtach.tmdbclient.data.remote.movie.model.Detail
import eu.wojtach.tmdbclient.data.remote.movie.model.Page
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.parameters
import org.koin.core.annotation.Factory

@Factory
class DataSource(
    private val ktorClient: HttpClient
) {

    suspend fun discovery(page: Int): Page {
        val result = ktorClient.get {
            url {
                encodedPath = "/3/discover/movie"
                parameters {
                    append("page", page.toString())
                }
            }
        }
        return result.body()
    }

    suspend fun details(movieId: Long): Detail {
        val result = ktorClient.get {
            url {
                encodedPath = "/3/movie/$movieId"
            }
        }
        return result.body()
    }
}

package eu.wojtach.tmdbclient.data.remote.genre

import eu.wojtach.tmdbclient.data.remote.genre.model.Genres
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import org.koin.core.annotation.Factory

@Factory
class DataSource(
    private val ktorClient: HttpClient
) {

    suspend fun genres(): Genres {
        val result = ktorClient.get {
            url {
                encodedPath = "/3/genre/movie/list"
            }
        }
        return result.body()
    }
}

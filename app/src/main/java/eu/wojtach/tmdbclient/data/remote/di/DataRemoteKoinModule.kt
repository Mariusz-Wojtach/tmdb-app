package eu.wojtach.tmdbclient.data.remote.di

import android.util.Log
import eu.wojtach.tmdbclient.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

@Module
class DataRemoteKoinModule {

    @Factory
    fun ktorClient(): HttpClient =
        HttpClient(OkHttp) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                }

                header(HttpHeaders.Authorization, "Bearer ${BuildConfig.TMDB_API_KEY}")
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 30_000
                requestTimeoutMillis = 30_000
                socketTimeoutMillis = 30_000
            }

            install(HttpRequestRetry) {
                maxRetries = 3
                retryOnExceptionIf { _, cause ->
                    Log.e("HTTP Client", "Retrying request", cause)
                    cause !is CancellationException
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("HTTP Client", message)
                    }
                }
                level = LogLevel.HEADERS
            }
        }
}

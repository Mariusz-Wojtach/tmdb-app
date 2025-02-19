package eu.wojtach.tmdbclient.data.remote.di

import android.util.Log
import eu.wojtach.tmdbclient.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class DataRemoteKoinModule {

    @Factory
    fun ktosClient(): HttpClient =
        HttpClient() {
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

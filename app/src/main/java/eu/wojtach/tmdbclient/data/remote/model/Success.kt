package eu.wojtach.tmdbclient.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Success<T>(
    val page: Long,
    val results: List<T>,
)

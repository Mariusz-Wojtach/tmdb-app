package eu.wojtach.tmdbclient.data.remote.movie.model

import kotlinx.serialization.Serializable

@Serializable
data class Page(
    val page: Long,
    val results: List<Discover>,
)

package eu.wojtach.tmdbclient.data.remote.genre.model

import kotlinx.serialization.Serializable

@Serializable
data class Genres(
    val genres: List<Genre>
)

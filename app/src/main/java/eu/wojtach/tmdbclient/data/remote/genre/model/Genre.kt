package eu.wojtach.tmdbclient.data.remote.genre.model

import eu.wojtach.tmdbclient.domain.model.Filter
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Long,
    val name: String
) {
    fun toDomain() : Filter = Filter(id, name)
}

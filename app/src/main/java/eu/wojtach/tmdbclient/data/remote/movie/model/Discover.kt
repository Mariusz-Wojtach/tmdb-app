package eu.wojtach.tmdbclient.data.remote.movie.model

import eu.wojtach.tmdbclient.domain.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data class Discover(
    val id: Long,
    val title: String,
    val vote_average: Float,
    val poster_path: String
) {
    fun toDomain() = Movie(
        id = id,
        title = title,
        posterPath = poster_path,
        rating = vote_average,
        details = null
    )
}
